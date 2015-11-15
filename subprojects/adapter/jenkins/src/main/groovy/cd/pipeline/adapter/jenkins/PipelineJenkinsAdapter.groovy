package cd.pipeline.adapter.jenkins

import cd.pipeline.script.PipelineDsl

class PipelineJenkinsAdapter {
    JenkinsSettings settings

    PipelineJenkinsAdapter(JenkinsSettings settings) {
        this.settings = settings
    }

    void apply(PipelineDsl pipeline) {

    }
}
