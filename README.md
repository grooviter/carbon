[![license](https://img.shields.io/github/license/grooviter/carbon.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Travis](https://img.shields.io/travis/grooviter/carbon.svg)](https://travis-ci.org/grooviter/carbon) [![Bintray](https://img.shields.io/bintray/v/grooviter/maven/carbon.svg)](https://bintray.com/grooviter/maven/carbon)

## What is Carbon ?

Carbon is a set of utilities to make it easier to develop http://www.groovy-lang.org scripts.

**IMPORTANT**: Carbon is still at alpha state.

## How to use it ?

```Groovy
#!/usr/bin/env groovy

carbon = [
    name: 'greetings',
    version: '1.0.0',
    description: '''
    This script says @|yellow hello |@ to the name
    passed as parameter:

    greetings --name John
    ''',
    options: [
        user: [type: String, required: true, description: 'Your name']
    ]
]

info "Hi there ${params.user}!"

```

If you make the script executable and execute `./greetings.groovy -h`:

```
NAME:
greetings (1.0.0)

SYNOPSIS:
greetings [-h] -u=PARAM

DESCRIPTION:
This script says hello  to the name
passed as parameter:

greetings --name John


OPTIONS:
  -h, --help         Shows help
  -u, --user=PARAM   Your name

AUTHORS:
Still no description
```

And if you execute `./greetings.groovy --user John`:

```
Hi there John!
```

## Installation

In order to use `Carbon` in your Groovy code you can find it in Bintray or Maven Central:

    repositories {
        jcenter() // or mavenCentral()
    }

Then you can add the dependency to your project:

    compile com.github.grooviter:carbon:0.1.6'
