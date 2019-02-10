@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'hash-files',
    version: '0.1.0',
    description: 'hashes files passed as parameters',
    params: [ // <1>
        files: [type: File[], arity: "1", description: "Files to get the hash from"]
    ],
    options: [
        algorithm: [
            type: String,
            description: 'MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512',
            defaultValue: "MD5"
        ]
    ]
]

files.each {
    info hash(algorithm, it) + "\t" + it
}
