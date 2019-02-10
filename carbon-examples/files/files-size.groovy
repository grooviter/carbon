#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0') // <1>
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'Show size of files',
    version: '0.1.0',
    description: 'Shows file size friendlier',
    params: [
        files: [type: File[], paramLabel: 'FILES', description: 'Files to inspect']
    ]
]

files.each { File file ->
    info "${file.name} \t ${fileSize(file)}"
}
