package cd.pipeline.script

import cd.pipeline.script.dsl.Tool
import com.google.common.collect.ImmutableSet

class PipelineDsl {
    private Set<Tool> require = ImmutableSet.of()

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
}
