[![license](https://img.shields.io/github/license/grooviter/carbon.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Travis](https://img.shields.io/travis/grooviter/carbon.svg)](https://travis-ci.org/grooviter/carbon) [![Bintray](https://img.shields.io/bintray/v/grooviter/maven/carbon.svg)](https://bintray.com/grooviter/maven/carbon)

## What is Carbon ?

Carbon is a set of utilities to make it easier to develop http://www.groovy-lang.org scripts.

## How to use it ?

```Groovy
@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
    name: 'greetings',
    version: '1.0.0',
    description: 'This script says hello to NAME',
    params: [
        name: [type: String, arity: "1", description: 'Your name']
    ]
]

println "Hi there ${name} from '${carbon.name}' script!"
```

Then you can ask the script to show you its help info:

```shell
groovy greetings.groovy -h
```

And you'd get:

```
Usage: lala [-hV] name
This script says hello  to NAME
      name        Your name
  -h, --help      Show this help message and exit.
  -V, --version   Print version information and exit.
```

Now that you know to use it you can execute:

```shell
groovy greetings.groovy --user John
```

You should see in the console the following greetings:

```
Hi there John!
```

## Documentation

You can find all Carbon's documentation at the [project's site](https://grooviter.github.io/carbon)