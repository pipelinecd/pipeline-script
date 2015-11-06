package cd.pipeline.script

trait DslRunner {
    ScriptResult runScript(final String script) {
        def output = new ByteArrayOutputStream()
        def stream = new PrintStream(output)
        final PipelineScriptRunner buildScript = new PipelineScriptRunner(stream, script)

        buildScript.run()

        return new ScriptResult(buildScript.script, output.toString())
    }
}
