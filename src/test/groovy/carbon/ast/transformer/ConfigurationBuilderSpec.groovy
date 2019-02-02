package carbon.ast.transformer

import org.codehaus.groovy.control.CompilePhase
import spock.lang.Specification
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.ConstantExpression

/**
 * Checks the functionality of class {@link ConfigurationBuilder}
 *
 * @since 0.2.0
 */
class ConfigurationBuilderSpec extends Specification {

    void 'Carbon\'s configuration from a MapEntryExpression'() {
        given: 'a carbon expression'
        MapExpression mapX = macro(CompilePhase.SEMANTIC_ANALYSIS) {
            [
                name:'simple',
                version:'1.0.1',
                description:'simple script',
                unknown:''.trim(),
                options:[
                    user:[type:String, required:true]
                ]
            ]
        }

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(mapX)

        when: 'extracting the configuration'
        Map<String, ?> config = builder.build()

        then: 'config should have the expected values'
        verifyAll(config) {
            name        == 'simple'
            version     == '1.0.1'
            description == 'simple script'

            options.user.type == String
            options.user.required == true
        }

        and: 'no recognized expressions are left out'
        !config.unknown
    }

    void 'Carbon\'s configuration from a string path'() {
        given: 'a carbon expression value'
        ConstantExpression pathX = macro { 'src/test/resources/carbon/ast/transformer/config.groovy' }

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(pathX)

        when: 'extracting the configuration'
        Map<String, ?> config = builder.build()

        then: 'config should have the expected values'
        config
    }

    void 'Carbon\'s configuration from a string path doesn\'t exist'() {
        given: 'a carbon expression value'
        ConstantExpression pathX = macro { 'src/test/resources/carbon/unknown.yaml' }

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(pathX)

        when: 'extracting the configuration'
        builder.build()

        then: 'config should throw an exception'
        thrown(IllegalStateException)
    }

    void 'Carbon\'s configuration from a MapEntryExpression with configuration'() {
        given: 'a carbon expression'
        MapExpression mapX = macro(CompilePhase.SEMANTIC_ANALYSIS) {
            [
                name:'simple-script',
                version:'1.0.1',
                configuration:'src/test/resources/carbon/ast/transformer/config.groovy',
            ]
        }

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(mapX)

        when: 'extracting the configuration'
        Map<String, ?> config = builder.build()

        then: 'some values are taken from config file'
        config.description.contains 'MD2, MD5'

        and: 'some others keep the value written in the code'
        config.version     == '1.0.1'
        config.name        == 'simple-script'

        and: 'check nested values'
        verifyAll(config) {
            options.algorithm.defaultValue == 'MD5'
            options.algorithm.type == String
        }
    }
}
