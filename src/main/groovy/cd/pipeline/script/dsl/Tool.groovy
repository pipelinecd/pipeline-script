package cd.pipeline.script.dsl

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.Sortable

@Immutable
@Sortable(includes = ['name'])
@EqualsAndHashCode(includes = ['name'])
class Tool {
    String name
    String version
}
