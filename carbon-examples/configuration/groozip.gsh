#!/usr/bin/env groovy
@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = 'groozip_cfg.groovy'

ant.zip(destFile: dest) {
    fileset(dir: source)
}
