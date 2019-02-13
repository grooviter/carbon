#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0') // <1>
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = 'banner-cfg.groovy'

info "Hello Ansi from Carbon!"
