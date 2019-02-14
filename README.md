[![license](https://img.shields.io/github/license/grooviter/carbon.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Travis](https://img.shields.io/travis/grooviter/carbon.svg)](https://travis-ci.org/grooviter/carbon) [![Bintray](https://img.shields.io/bintray/v/grooviter/maven/carbon.svg)](https://bintray.com/grooviter/maven/carbon)

## What is Carbon ?

Carbon is a set of utilities to make it easier to develop http://www.groovy-lang.org scripts.

**IMPORTANT**: Carbon is still at *ALPHA* state.

## How to use it ?

```Groovy
@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'greetings',
    version: '1.0.0',
    description: 'This script says @|yellow hello |@ to NAME',
    params: [
        name: [type: String, arity: "1", description: 'Your name']
    ]
]

println "Hi there ${name}!"

```

Then you can execute

```shell
groovy greetings.groovy -h
```

And you'd get:

```

```

And if you executed

```shell
groovy greetings.groovy --user John
```

You will see in the console the following greetings:

```
Hi there John!
```

## Documentation

You can find all the documentation, and API Groovydoc at the https://grooviter.github.io/carbon[project's site]

## Installation

In order to use `Carbon` in your Groovy code you can find it in Bintray or Maven Central:

```groovy
repositories {
    jcenter() // or mavenCentral()
}
```

Then you can add the dependency to your project:

```groovy
compile com.github.grooviter:carbon:0.1.6'
```