#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import static java.util.Collections.shuffle

carbon = 'random-quotes-cfg.gsh'

List<String> quotes = configuration.quotes as List<String>

shuffle(quotes)

println ansi(quotes.find())
