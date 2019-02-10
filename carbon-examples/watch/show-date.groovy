#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import java.util.concurrent.TimeUnit

carbon = [
    name: 'show-date',
    version: '0.1.0',
    description: 'Simply shows the system date every 4 seconds',
]

watch(4, TimeUnit.SECONDS) {
    println clear().render("@|yellow date:|@ ${new Date()}")
}
