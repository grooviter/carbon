#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import java.util.concurrent.TimeUnit

carbon = [
    name: 'sync',
    version: '0.1.0',
    description: 'Copies new files from source dir to destination dir',
    options: [
        from: [type: File, description: 'Source directory', required: true],
        to: [type: File, description: 'Destination directory', required: true],
        interval: [type: Integer, description: 'Seconds to wait', defaultValue: 10]
    ]
]

watch(interval, TimeUnit.SECONDS) {
    ant.copy(toDir: to) {  // <1>
        fileset(dir: from)
    }
}
