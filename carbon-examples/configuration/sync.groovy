#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import java.util.concurrent.TimeUnit

carbon = 'sync_config.groovy' // <1>

watch(interval, TimeUnit.SECONDS) {
    ant.copy(toDir: to) {
        fileset(dir: from)
    }
}
