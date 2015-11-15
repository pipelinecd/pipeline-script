package cd.pipeline.script.api;

import java.util.Map;

public interface Job {
    void exec(String cmd);

    void artifact(Map<String, ?> artifactSpec);
}
