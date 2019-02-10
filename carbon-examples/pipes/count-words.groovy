#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'count-words',
    version: '0.1.0',
    description: 'Counts the words contained in the INPUT string passed as parameter',
    params: [
        input: [
            type: String,        // <1>
            arity: "0..1",       // <2>
            paramLabel: 'INPUT',
            description: 'String containing the input'
        ]
    ]
]

boolean hasPipe = hasPipedIn()   // <3>

if (input) {                     // <4>
    println input.split().size()
}

if (!input && hasPipe) {         // <5>
    println pipedIn()            // <6>
        .collect()
        .collectMany { it.split().collect() }
        .size()
}

if (!(input || hasPipe)) {       // <7>
    printHelpMessage(getOrCreateCommandLine())
}
