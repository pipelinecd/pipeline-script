package cd.pipeline.script.dsl

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import spock.lang.Specification

class ToolTest extends Specification {

    def "Tool is @Immutable"() {
        expect:
        def annotation = Tool.getAnnotation(Immutable)
        annotation.copyWith() == false
        annotation.knownImmutableClasses() == []
        annotation.knownImmutables() == []
    }

    def "Tool is @EqualsAndHashCode on 'name'"() {
        expect:
        def annotation = Tool.getAnnotation(EqualsAndHashCode)
        annotation.includes() == ['name']
        annotation.useCanEqual() == true
        annotation.cache() == false
        annotation.callSuper() == false
        annotation.excludes() == []
        annotation.includeFields() == false
    }

    def "Tool is equalized on 'name'"() {
        def v1 = new Tool('name', '1')
        def v2 = new Tool('name', '2')

        expect:
        v1.equals(v2)
        v1 == v2
    }
}
