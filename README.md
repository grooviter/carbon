[![license](https://img.shields.io/github/license/grooviter/carbon.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Travis](https://img.shields.io/travis/grooviter/carbon.svg)](https://travis-ci.org/grooviter/carbon) [![Bintray](https://img.shields.io/bintray/v/grooviter/maven/carbon.svg)](https://bintray.com/grooviter/maven/carbon)

## What is Carbon ?

Carbon is a set of utilities to make it easier to develop http://www.groovy-lang.org scripts.

## How to use it ?

```Groovy
#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon:0.1.5')
import carbon.Cli

name: "hello-world"
version: "1.0.0"
description:
"""This script says @|yellow hello |@ to the name
passed as parameter:

hello-word --name John
"""

params: [
    user: [
        type: String,
        description: 'Your name',
        mandatory: true
    ]
]

script:
Cli.withConfig()
    .logger()
    .logln("Hello ${params.user}")

```

If you make the script executable and execute `./hellow-world.groovy -h`:

```
NAME:
hello-world (1.0.0)

SYNOPSIS:
hello-world [-h] -u=PARAM

DESCRIPTION:
This script says hello  to the name
passed as parameter:

hello-word --name John


OPTIONS:
  -h, --help         Shows help
  -u, --user=PARAM   Your name

AUTHORS:
Still no description
```

And if you execute `./hellow-world.groovy --user John`:

```
Hello John
```

## Installation

In order to use `Carbon` in your Groovy code you can find it in Bintray or Maven Central:

    repositories {
        jcenter() // or mavenCentral()
    }

Then you can add the dependency to your project:

    compile com.github.grooviter:carbon:0.1.5'
