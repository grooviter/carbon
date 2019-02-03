name = "hello-carbon"

description = '''\
MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512 \
or any other MessageDigest algorithm.\
'''

options = [
    algorithm: [
        type: String,
        defaultValue: 'MD5',
        description: '''\
        MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512 \
        or any other MessageDigest algorithm.\
        '''
    ],
    help: [
        type: Boolean,
        usageHelp: true,
        description: 'Show this message and exit'
    ]
]

params = [
    files: [
        type: File[],
        arity: "1..2",
        paramlabel: 'FILES',
        description: 'The file(s) whose checksum to calculate.'
    ]
]
