package cd.pipeline.script.api;

import groovy.lang.Closure;

public interface Stage {
    void job(Closure cl);
}
