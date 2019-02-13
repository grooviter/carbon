package carbon.ast.config

import org.codehaus.groovy.control.CompilePhase
import spock.lang.Specification
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.ConstantExpression

/**
 * Checks the functionality of class {@link carbon.ast.config.ConfigurationBuilder}
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
        ConstantExpression pathX = macro { 'src/test/resources/carbon/ast/config/config.groovy' }

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
}
