package cd.pipeline.script.dsl

stage('stage1') {
    job {
        exec './gradlew clean build'

        // Archive artifacts for use in other stages
        artifact id: 'something1', content: text('lala')
        artifact id: 'jars1', content: files('build/lib/*.jar')
        // or to be able to use the artifacts (eg. test reports/data) outside the pipeline, eg. in UI
        artifact id: 'testReports1', content: files('build/reports', 'build/test-results/**/*.xml')
    }
}

stage('stage2') {
    job {
        exec './gradlew clean build'

        // Archive artifacts for use in other stages
        artifact id: 'something2', content: text('lala')
        artifact id: 'jars2', content: files('build/lib/*.jar')
        // or to be able to use the artifacts (eg. test reports/data) outside the pipeline, eg. in UI
        artifact id: 'testReports2', content: files('build/reports', 'build/test-results/**/*.xml')
    }
}

stage('stage3') {
    job {
        exec './gradlew clean build'

        // Archive artifacts for use in other stages
        artifact id: 'something3', content: text('lala')
        artifact id: 'jars3', content: files('build/lib/*.jar')
        // or to be able to use the artifacts (eg. test reports/data) outside the pipeline, eg. in UI
        artifact id: 'testReports3', content: files('build/reports', 'build/test-results/**/*.xml')
    }
}
