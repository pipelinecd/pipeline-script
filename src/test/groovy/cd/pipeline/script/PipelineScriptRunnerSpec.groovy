package cd.pipeline.script

import cd.pipeline.script.dsl.Tool
import spock.lang.Specification
import spock.lang.Unroll

class PipelineScriptRunnerSpec extends Specification
    implements DslRunner {

    def 'Script may be empty'() {
        def script = """
        """

        expect:
        def result = runScript(script)
        result.script.require.asList() == []
        result.output == ''
    }

    def 'Can read the requirements'() {
        def script = """
            print require
        """

        expect:
        def result = runScript(script)
        result.script.require.asList() == []
        result.output == '[]'
    }

    @Unroll
    def 'Can define the requirements in #style style'() {
        expect:
        def result = runScript(script)
        result.script.require.asList() == [
            new Tool('myName', 'myVersion')
        ]
        result.output == ''

        where:
        style        | script
        'Java'       | """require(new Tool('myName', 'myVersion'))"""
        'Groovy-map' | """require(new Tool(name: 'myName', version: 'myVersion'))"""
    }
}
