package cd.pipeline.script

abstract class PipelineScript extends Script {
    @Delegate
    private PipelineDsl pipeline

    void init(final PipelineDsl pipeline) {
        this.pipeline = pipeline
    }
}
