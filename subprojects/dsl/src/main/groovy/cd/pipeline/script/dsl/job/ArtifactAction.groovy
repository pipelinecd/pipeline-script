package cd.pipeline.script.dsl.job

class ArtifactAction implements Action {
    final String id
    final Object content

    ArtifactAction(String id, Object content) {
        this.id = id
        this.content = content
    }
}
