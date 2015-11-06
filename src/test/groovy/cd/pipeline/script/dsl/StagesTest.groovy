package cd.pipeline.script.dsl

import cd.pipeline.script.DslRunner
import cd.pipeline.script.ScriptLoader
import cd.pipeline.script.dsl.job.ArtifactAction
import cd.pipeline.script.dsl.job.ExecAction
import spock.lang.Specification

class StagesTest extends Specification
    implements DslRunner, ScriptLoader {

    def 'Action addition order is kept per Job of a Stage '() {
        def script = load('SingleStage.groovy')

        expect:
        def result = runScript(script)
        result.script.stages.size() == 1
        StageSpec stage = result.script.stages.first()
        stage.jobs.size() == 1

        JobSpec job = stage.jobs.first()
        job.actions.size() == 4
        def action0 = job.actions.get(0)
        action0 instanceof ExecAction
        action0.command == './gradlew clean build'

        def action1 = job.actions.get(1)
        action1 instanceof ArtifactAction
        action1.id == 'something'
        // TODO: find way to check action1.content

        def action2 = job.actions.get(2)
        action2 instanceof ArtifactAction
        action2.id == 'jars'
        // TODO: find way to check action2.content

        def action3 = job.actions.get(3)
        action3 instanceof ArtifactAction
        action3.id == 'testReports'
        // TODO: find way to check action3.content

        result.output == ''
    }
}
