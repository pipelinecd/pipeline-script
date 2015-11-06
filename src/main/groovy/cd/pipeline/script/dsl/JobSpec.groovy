package cd.pipeline.script.dsl

import cd.pipeline.script.dsl.job.*
import com.google.common.collect.ImmutableList

class JobSpec {
    private List<Action> actions = ImmutableList.of()

    @Delegate(includeTypes = ArtifactContent)
    private ArtifactSpec artifactSpec = new ArtifactSpec()

    void exec(String cmd) {
        addAction(new ExecAction(cmd))
    }

    void artifact(Map<String, ?> artifactSpec) {
        // TODO: make syntax stricter
        addAction(new ArtifactAction(artifactSpec.id, artifactSpec.content))
    }

    List<Action> getActions() {
        return actions
    }

    private void addAction(Action action) {
        actions = ImmutableList.builder()
            .addAll(actions)
            .add(action)
            .build()
    }
}
