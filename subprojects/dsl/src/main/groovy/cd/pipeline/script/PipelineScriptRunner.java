package cd.pipeline.script;

import cd.pipeline.script.dsl.ToolSpec;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

import java.io.PrintStream;

public class PipelineScriptRunner {
    private final PipelineScript script;
    private final PrintStream redirectedOutput;
    private PrintStream originalStdOut;
    private PrintStream originalStdErr;

    public PipelineScriptRunner(final PrintStream output, final String scriptText) {
        redirectedOutput = output;
        CompilerConfiguration config = new CompilerConfiguration(CompilerConfiguration.DEFAULT);
        config.setScriptBaseClass(PipelineScript.class.getName());

        ImportCustomizer importCustomizer = new ImportCustomizer();
        importCustomizer.addStarImports(ToolSpec.class.getPackage().getName());

        SecureASTCustomizer secureCustomizer = new SecureASTCustomizer();

        config.addCompilationCustomizers(importCustomizer, secureCustomizer);

        final GroovyShell shell = new GroovyShell(config);
        script = (PipelineScript) shell.parse(scriptText); // TODO: Add original filename? Needed?

        script.init(new PipelineDsl());
    }

    public void run() {
        try {
            startCapturingOutput();
            script.run();
        } finally {
            stopCapturingOutput();
        }
    }

    private void startCapturingOutput() {
        originalStdOut = System.out;
        System.setOut(redirectedOutput);
        originalStdErr = System.err;
        System.setErr(redirectedOutput);
    }

    private void stopCapturingOutput() {
        try {
            System.setOut(originalStdOut);
            System.setErr(originalStdErr);
            redirectedOutput.flush();
        } finally {
            originalStdOut = null;
            originalStdErr = null;
        }
    }
}
