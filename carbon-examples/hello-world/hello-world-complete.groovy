@Grab('com.github.grooviter:carbon-core:0.2.0') // <1>
@Grab('org.slf4j:slf4j-simple:1.7.25')

carbon = [
   name: 'hello-world',
   version: '0.1.0',
   description: 'Carbon says Hello Groovy to the NAME!',
   options: [
       upperCase: [type: Boolean, description: 'shows message in uppercase', defaultValue: false]
   ],
   params: [
       name: [type: String, arity: "1", paramLabel: "NAME", description: "Your name"]
   ]
]

String message = "Hello $name from Carbon"

info upperCase ? message.toUpperCase() : message
