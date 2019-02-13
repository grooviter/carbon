@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'datetime',
    version: '0.1.0',
    description: 'shows current date with the format',
    options: [
        format: [                                          // <1>
            type: String,                                  // <2>
            description: 'Date format (${DEFAULT-VALUE})', // <3>
            defaultValue: 'dd/MM/yyyy HH:mm:ss'            // <4>
        ]
    ]'
]

println new Date().format(format) // <5>
