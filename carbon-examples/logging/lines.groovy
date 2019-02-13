#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import org.fusesource.jansi.AnsiRenderer

carbon = [
    name: "lines",
    description: "logging example"
]

info "This is an info line"   // <1>

debug "This is a debug line"  // <2>

error "This is an error line" // <3>
