script(
    version: 0.1
)

require(
    name: 'myName', version: 1.0.0        // tool is the default, therefor this is the same as "tool name: 'myName', version: 'myVersion'"
    tool name: 'myName', version: 0.1.5
    plugin name: 'myName', version: 0.23  // only for CI tools that require plugins, would like to not have this in here
)

stage('commit') {

}

stage(id: 'acceptance', title: 'ACC') {

}

stage('release') {

}

pipeline(type: branch(/^feature\/*/)) {
    stages(
        'commit'
        'acceptance'
    )
}

pipeline(type: pullrequest) {
    stages('commit')
}

// == default pipeline
pipeline {
    stages(
        'commit'
        'acceptance'
        'release'
    )
}
