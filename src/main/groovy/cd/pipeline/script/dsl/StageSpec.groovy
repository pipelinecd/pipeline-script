package cd.pipeline.script.dsl

import cd.pipeline.script.util.ConfigureUtil
import com.google.common.collect.ImmutableSet

class StageSpec {
    final String id
    private Set<StageSpec> jobs = ImmutableSet.of()

    StageSpec(String id) {
        this.id = id
    }

    void job(@DelegatesTo(value = JobSpec, strategy = Closure.DELEGATE_FIRST) Closure cl) {
        def job = new JobSpec()
        def clone = cl.rehydrate(job, this, this)
        ConfigureUtil.configure(job, clone)
        jobs = ImmutableSet.builder()
            .addAll(jobs)
            .add(job)
            .build()

    }

    Set<StageSpec> getJobs() {
        return jobs
    }
}
