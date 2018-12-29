package carbon.ast

import static groovy.test.GroovyAssert.assertScript
import carbon.spec.CarbonSpec
import spock.lang.Specification

class ScriptSyntaxSpec extends CarbonSpec {

    void "check basic blocks to be valid"() {
        expect: 'basic blocks to pass syntax validation'
        evaluateScript '''           name: "carbon-example"
           version: "0.1.0"
           author: "@marioggar"
           desc: "Sample Carbon script"
           script:

           println "Hello World"
        '''
    }

    void "check params to be valid"() {
        expect: 'basic blocks to pass syntax validation'
        evaluateScript '''
           name: "carbon-example"
           version: "0.1.0"
           author: "@marioggar"
           desc: "Sample Carbon script"
           params: [
             name: [type: String, mandatory: true]
           ]
           script:

           println "Hello ${params.name}"
        '''
    }
}
