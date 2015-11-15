package cd.pipeline.script.dsl

stage('commit') {
    job {
        exec './gradlew clean build'

        // Archive artifacts for use in other stages
        artifact id: 'something', content: text('lala')
        artifact id: 'jars', content: files('build/lib/*.jar')
        // or to be able to use the artifacts (eg. test reports/data) outside the pipeline, eg. in UI
        artifact id: 'testReports', content: files('build/reports', 'build/test-results/**/*.xml')
    }
}
