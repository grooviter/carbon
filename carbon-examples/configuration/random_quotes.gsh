#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = 'random_quotes_cfg.gsh'

carbonConfig.quotes.suffle()

println carbonConf.quotes
