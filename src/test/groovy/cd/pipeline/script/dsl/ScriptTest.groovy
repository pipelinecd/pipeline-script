package cd.pipeline.script.dsl

import spock.lang.Specification

class ScriptTest extends Specification {

    def "Artifact IDs must be unique for the whole script"() {
        expect:
        false
    }

    def "Stage IDs must be unique for the whole script"() {
        expect:
        false
    }

    def "Job IDs must be unique for the whole script"() {
        expect:
        false
    }

    def "Artifact with ID must exist before can be fetched"() {
        expect:
        false
    }
}
