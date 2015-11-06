package cd.pipeline.script.dsl

import cd.pipeline.script.PipelineDsl
import spock.lang.Specification

class RequirementsTest extends Specification {
    private PipelineDsl script

    void setup() {
        script = new PipelineDsl()
    }

    def "Requirement may only be set once"() {
        when:
        script.require(new Tool(name: 'mytool', version: '1'))
        script.require(new Tool(name: 'lala', version: '2'))

        then:
        def error = thrown(IllegalStateException)
        error.message == "'require' can only be set once"
    }

    def "Requirement may not contains duplicates of tools with the same name"() {
        when:
        script.require(
            new Tool(name: 'mytool', version: '1.2.3'),
            new Tool(name: 'mytool', version: '1.2.4'),
        )

        then:
        def error = thrown(IllegalStateException)
        error.message == "'require' contains duplicates"
    }

    def "Requirement is immutable"() {
        when:
        script.require.add(null)

        then:
        thrown(UnsupportedOperationException)
    }
}
