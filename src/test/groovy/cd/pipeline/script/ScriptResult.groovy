package cd.pipeline.script

class ScriptResult {
    final PipelineScript script
    final String output

    def ScriptResult(PipelineScript script, String output) {
        this.output = output
        this.script = script
    }
}
