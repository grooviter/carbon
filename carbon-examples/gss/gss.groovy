#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0') // <1>
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'gss (Groovy Static Server)',
    version: '0.1.0',
    description: 'Serves a given DIR in a specific port',
    options: [
        port: [type: Integer, defaultValue: 9090, description: 'Port to serve content (${DEFAULT-VALUE})']
    ],
    params: [
        dir: [type: File, arity: "1", paramLabel: 'DIR', description: 'Directory to serve']
    ]
]

watch {
    println "serving path '${dir.absolutePath}' at port '${port}'"
}
