package cd.pipeline.adapter.jenkins

import groovy.transform.Immutable

@Immutable
class JenkinsSettings {
    String url
    String privateKeyString
    String privateKeyPassword
}
