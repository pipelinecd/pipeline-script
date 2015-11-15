package cd.pipeline.script.dsl.job

class ArtifactSpec implements ArtifactContent {
    String id
    ArtifactContent content

    ArtifactContent text(String text) {
        // TODO: add impl
        new ArtifactSpec()
    }

    ArtifactContent files(String... text) {
        // TODO: add impl
        new ArtifactSpec()
    }
}
