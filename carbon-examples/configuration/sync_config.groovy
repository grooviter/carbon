name = 'sync'
version = '0.1.0'

description = 'Copies new files from source dir to destination dir'

options = [
    from: [
        type: File,
        description: 'Source directory',
        required: true
    ],
    to: [
        type: File,
        description: 'Destination directory',
        required: true
    ],
    interval: [
        type: Integer,
        description: 'Seconds to wait',
        defaultValue: 10
    ]
]
