package cd.pipeline.script.dsl

import cd.pipeline.script.api.Tool
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.Sortable

@Immutable
@Sortable(includes = ['name'])
@EqualsAndHashCode(includes = ['name'])
class ToolSpec implements Tool {
    String name
    String version
}
