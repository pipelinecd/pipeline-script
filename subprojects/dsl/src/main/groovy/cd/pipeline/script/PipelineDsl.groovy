package cd.pipeline.script

import cd.pipeline.script.api.Pipeline
import cd.pipeline.script.api.Tool
import cd.pipeline.script.dsl.StageSpec
import cd.pipeline.script.util.ConfigureUtil
import com.google.common.collect.ImmutableSet

class PipelineDsl implements Pipeline {
    private Set<Tool> require = ImmutableSet.of()
    private Set<StageSpec> stages = ImmutableSet.of()

    void require(Tool... tools) {
        if (require) {
            // TODO: make specific
            throw new IllegalStateException("'require' can only be set once")
        }
        if (tools.toUnique().size() != tools.length) {
            // TODO: make specific, incl. show the duplicates
            throw new IllegalStateException("'require' contains duplicates")
        }
        require = ImmutableSet.copyOf(tools)
    }

    Set<Tool> getRequire() {
        ImmutableSet.copyOf(require)
    }

    void stage(String id, @DelegatesTo(value = StageSpec, strategy = Closure.DELEGATE_FIRST) Closure cl) {
        def stage = new StageSpec(id)
        def clone = cl.rehydrate(stage, this, this)
        ConfigureUtil.configure(stage, clone)
        stages = ImmutableSet.builder()
            .addAll(stages)
            .add(stage)
            .build()
    }

    Set<StageSpec> getStages() {
        return stages
    }
}
