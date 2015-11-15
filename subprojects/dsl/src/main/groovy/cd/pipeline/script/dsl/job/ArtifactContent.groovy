package cd.pipeline.script.dsl.job

interface ArtifactContent {
    // TODO: make this something that can output its content in a generic way
    ArtifactContent text(String text)

    ArtifactContent files(String... text)
}
