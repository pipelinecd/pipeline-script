/**
 * The basic pipeline
 *
 * This pipeline must represent a basic pipeline for a single application/(micro)service.
 * - A pipeline run is always executed from end-to-end until a failure stops the line.
 * - A pipeline is triggered by default by a commit/push, but this is handled by the CD tool
 *   and/or pipeline-script adapter/importer that is triggered by a hook from the SCM system (eg. git).
 * - The pipeline doesn't know anything about the SCM system, as it expects that each job is always
 *   run in a workspace containing the data it should work on. Additional resources can be fetched
 *   with normal exec commands as you would normally.
 *
 * Important characteristics:
 * - there are 4 built-in, powerful abstractions and their relationship:
 *   Tasks/Execs inside Jobs inside Stages inside Pipelines
 * - the fact that some are executed in parallel (depending on salve/agents availability) while others sequentially:
 *   - Multiple Pipelines run in parallel
 *   - Multiple Stages within a Pipeline run sequentially
 *   - Multiple Jobs within a Stage run in parallel
 *   - Multiple Tasks/Execs within a Job run sequentially
 * - no optional pipeline parts
 */

/**
 * Things to look at:
 * - How do artifacts flow through the pipeline
 * - Take into account the different types of artifacts (do we need this, or are file collections enough?)
 *   (xml, jar, complete directories, docker images, etc.)
 * - The syntax should make it possible to verify the pipeline structure
 *   before executing it, incl. artifacts flowing through it
 * - Different types of incoming pipeline triggers
 *   - SCM (git, svn, etc.) (web)hook:
 *     - pull-request
 *     - specific branches (eg. via pattern matching)
 *     - else
 *   - other pipeline
 *     but isn't this basically just a SCM (web)hook,
 *     as this pipeline only knows about the repo its in
 *   - manual trigger
 *     - with/without parameters
 *
 * Questions:
 * - Can the working be the same as a unix pipe? With a super generic interface
 *   - Is this needed?
 */

script(
    // pipeline-script version
    version: 0.1

    // include pipeline-script extensions
    extend: from('repo.url') {
        id 'com.company.name1', version '1.0.0'
        id 'com.company.name2', version '1.2.0'
    }
)

require(
    name: 'git', version: '2.6.2'
    name: 'openjdk', version: '1.8.0_66'
)

stage('commit') {
    job {
        exec './gradlew clean build'

        // Archive artifacts for use in other stages
        artifact id: 'something', content: text('$VAR_TO_DOCKER_REF')
        artifact id: 'jarsUrl', content: textFromFile('artifactUrl.txt')
        artifact id: 'jars', content: files('build/lib/*.jar')
        // or to be able to use the artifacts (eg. test reports/data) outside the pipeline, eg. in UI
        artifact id: 'testReports', content: files('build/reports', 'build/test-results/**/*.xml')
    }
}

stage(id: 'acceptance', title: 'ACC') {
    // jobs run in parallel in a stage
    job(id: 'acceptance-windows', tags: ['windows']) {
        fetch id: 'something', into: 'something.txt'
        fetch id: 'jars', into: directory('libs')
        require 'build/lib/myjar.jar', into: directory('.')

        exec './gradlew integration-test-windows'
    }

    job(id: 'acceptance-linux', tags: ['linux']) {
        fetch 'build/lib/*.jar'

        exec './gradlew integration-test-linux'
    }
    // stage is success if all jobs are finished and successful
}

stage('release') {
    job {
        exec './gradlew release'
        exec '''git tag --annotate --file - v$(source version.properties; ${version}) <<<\
            v$(source version.properties; ${version})

            Release version $(source version.properties; ${version}) containing
            ${cat CHANGELOG.md}
            '''
        exec 'git push origin v$(source version.properties; ${version})'

        artifact id: 'package', content: files('build/distribution/*')
    }
}

// pipeline that is triggered by a change on a branch starting with the name 'feature/'
pipeline(type: branch(/^feature\/*/)) {
    // syntax conflict: it may seem that stages are run in parallel like jobs
    stages('commit')
    stages('acceptance')
}

// pipeline that is triggered by an incoming pullrequest or a change on a pullrequest
pipeline(type: pullrequest) {
    stages('commit')
}

// pipeline that is triggered in all other cases
pipeline {
    stages(
        'commit'
        'acceptance'
        manual('release')
    )
}
