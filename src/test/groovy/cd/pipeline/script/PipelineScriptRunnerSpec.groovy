package cd.pipeline.script

import cd.pipeline.script.dsl.Tool
import spock.lang.Specification
import spock.lang.Unroll

class PipelineScriptRunnerSpec extends Specification {

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

    private ScriptResult runScript(final String script) {
        def output = new ByteArrayOutputStream()
        def stream = new PrintStream(output)
        final PipelineScriptRunner buildScript = new PipelineScriptRunner(stream, script)

        buildScript.run()

        return new ScriptResult(buildScript.script, output.toString())
    }
}
