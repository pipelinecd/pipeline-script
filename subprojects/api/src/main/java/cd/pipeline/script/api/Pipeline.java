package cd.pipeline.script.api;

import groovy.lang.Closure;

public interface Pipeline {
    void require(Tool... tools);

    void stage(String id, Closure cl);
}
