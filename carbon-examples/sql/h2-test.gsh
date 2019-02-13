#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
@Grab('com.h2database:h2:1.4.196')
@GrabConfig(systemClassLoader=true)
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

carbon = [
    name: 'h2-test',
    description: 'database example',
    configuration: 'h2-test.yaml'
]

info "Load Sql object"
Sql sql = sql()                                                     // <1>

info "Create table"
sql.execute(configuration.queries.create)                           // <2>

info "Truncate table"
sql.execute(configuration.queries.truncate)                         // <3>

info "Insert records"
sql.withBatch(10, configuration.queries.insert) { stmt ->           // <4>
    (20..30).each { Integer id ->
        stmt.addBatch(id, "user", id)
    }
}

info "Select rows"
List<GroovyRowResult> rows = sql.rows(configuration.queries.select) // <5>

info "Print rows"
println ansi(rows)                                                  // <6>

info "End of ${carbon.name}"