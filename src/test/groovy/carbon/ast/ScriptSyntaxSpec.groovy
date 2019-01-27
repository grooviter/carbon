package carbon.ast

import carbon.spec.CarbonSpec

/**
 * This spec makes sure scripts compile correctly
 *
 * @since 0.1.0
 */
@SuppressWarnings('GStringExpressionWithinString')
class ScriptSyntaxSpec extends CarbonSpec {

    void "check basic blocks to be valid"() {
        expect: 'basic blocks to pass syntax validation'
        evaluateScript '''
           carbon = [
             name: "carbon-example-1",
             version: "0.1.0",
             author: "@marioggar",
             desc: "Sample Carbon script",
           ]

           println "Hello World"
        '''
    }

    void "check params to be valid"() {
        expect: 'basic blocks to pass syntax validation'
        evaluateScript '''
           carbon = [
             name: "carbon-example-2",
             version: "0.1.0",
             author: "@marioggar",
             desc: "Sample Carbon script",
             params: [
               name: [type: String, mandatory: true]
             ],
           ]

           println "Hello ${params.name}"
        '''
    }

    void 'Check '() {
        expect:
        evaluateScript '''
           import carbon.Cli

           carbon = [
             name: "carbon-example-3",
             version: "0.1.0",
             author: "@marioggear",
             desc: "Sample Carbon script",
             params: [
               name: [type: String, mandatory: true]
             ],
           ]

           Cli.withConfig().logger.logln  "Hello ${params.name}"
        '''
    }
}
