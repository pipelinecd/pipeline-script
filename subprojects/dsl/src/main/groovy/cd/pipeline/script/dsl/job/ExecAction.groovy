package cd.pipeline.script.dsl.job

class ExecAction implements Action {
    final String command

    ExecAction(String command) {
        this.command = command
    }
}
