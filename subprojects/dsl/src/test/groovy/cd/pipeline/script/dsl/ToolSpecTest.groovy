package cd.pipeline.script.dsl

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import spock.lang.Specification

class ToolSpecTest extends Specification {

    def "Tool is @Immutable"() {
        expect:
        def annotation = ToolSpec.getAnnotation(Immutable)
        annotation.copyWith() == false
        annotation.knownImmutableClasses() == []
        annotation.knownImmutables() == []
    }

    def "Tool is @EqualsAndHashCode on 'name'"() {
        expect:
        def annotation = ToolSpec.getAnnotation(EqualsAndHashCode)
        annotation.includes() == ['name']
        annotation.useCanEqual() == true
        annotation.cache() == false
        annotation.callSuper() == false
        annotation.excludes() == []
        annotation.includeFields() == false
    }

    def "Tool is equalized on 'name'"() {
        def v1 = new ToolSpec('name', '1')
        def v2 = new ToolSpec('name', '2')

        expect:
        v1.equals(v2)
        v1 == v2
    }
}
