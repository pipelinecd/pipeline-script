package cd.pipeline.script

trait ScriptLoader {
    String load(String scriptName) {
        this.class.getResource(scriptName).text
    }
}
