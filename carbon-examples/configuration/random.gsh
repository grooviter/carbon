#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import static java.lang.Math.*

carbon = [
    name: 'random',
    version: '0.1.0',
    description: 'Shows random numbers',
    configuration: 'random.yaml'            // <1>
]

Integer max = configuration.max as Integer  // <2>
Integer min = configuration.min as Integer

println round(floor(random() * max) + min)