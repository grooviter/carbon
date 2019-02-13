name = 'groozip'
version = '0.1.0'
description = 'Creates a zip file from the content of a DIR'

options = [
    source: [
        type: File,
        description: 'Source directory',
        required: true
    ],
    dest: [
        type: File,
        description: 'Destination directory',
        required: true
    ]
]

header = $/
@|yellow    ______                               _____   _       |@
@|yellow   / ____/________  ____ _   ____  __   /__  /  (_)___   |@
@|yellow  / / __/ ___/ __ \/ __ \ | / / / / /     / /  / / __ \  |@
@|yellow / /_/ / /  / /_/ / /_/ / |/ / /_/ /     / /__/ / /_/ /  |@
@|yellow \____/_/   \____/\____/|___/\__, /     /____/_/ .___/   |@
@|yellow                            /____/            /_/        |@
/$

footerHeading = '%nFor more details, see:%n'
footer = '@|yellow [1]|@ https://grooviter.github.io/carbon'
