package cd.pipeline.script.dsl

import cd.pipeline.script.DslRunner
import cd.pipeline.script.ScriptLoader
import cd.pipeline.script.dsl.job.Action
import cd.pipeline.script.dsl.job.ArtifactAction
import cd.pipeline.script.dsl.job.ExecAction
import spock.lang.Specification

class StagesTest extends Specification
    implements DslRunner, ScriptLoader {

    def 'Action addition order is kept per Job of a Stage'() {
        def script = load('SingleStage.groovy')

        when:
        def result = runScript(script)

        then:
        result.script.stages.size() == 1
        StageSpec stage = result.script.stages.first()
        stage.jobs.size() == 1

        JobSpec job = stage.jobs.first()
        job.actions.size() == 4

        verifyExecAction(job.actions.get(0), './gradlew clean build')
        verifyArtifactAction(job.actions.get(1), 'something')
        verifyArtifactAction(job.actions.get(2), 'jars')
        verifyArtifactAction(job.actions.get(3), 'testReports')

        result.output == ''
    }

    def 'Stage addition order is kept'() {
        def script = load('MultiStage.groovy')

        when:
        def result = runScript(script)

        then:
        result.script.stages.size() == 3

        def stages = result.script.stages.iterator()
        stages.next().id == 'stage1'
        stages.next().id == 'stage2'
        stages.next().id == 'stage3'

        result.output == ''
    }

    private void verifyExecAction(Action action, String command) {
        assert action instanceof ExecAction
        assert action.command == command
    }

    private void verifyArtifactAction(Action action, String id) {
        assert action instanceof ArtifactAction
        assert action.id == id
        // TODO: find way to check action.content
    }
}
