package carbon.ast.transformer

import spock.lang.Specification
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import asteroid.Expressions as X

/**
 * Checks the functionality of class {@link ConfigurationBuilder}
 *
 * @since 0.2.0
 */
class ConfigurationBuilderSpec extends Specification {

    void 'Carbon\'s configuration from a MapEntryExpression'() {
        given: 'a carbon expression'
        MapExpression mapX = X.mapX(
            X.mapEntryX(X.constX('name'), X.constX('simple-script')),
            X.mapEntryX(X.constX('version'), X.constX('1.0.1')),
            X.mapEntryX(X.constX('description'), X.constX('simple script'))
        )

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(mapX)

        when: 'extracting the configuration'
        Map<String, ?> config = builder.build()

        then: 'config should have the expected values'
        verifyAll(config) {
            name        == 'simple-script'
            version     == '1.0.1'
            description == 'simple script'
        }
    }

    void 'Carbon\'s configuration from a string path'() {
        given: 'a carbon expression value'
        ConstantExpression pathX = X.constX('src/test/resources/carbon/sql.yaml')

        and: 'an instance of ConfigurationBuilder'
        ConfigurationBuilder builder = new ConfigurationBuilder(pathX)

        when: 'extracting the configuration'
        Map<String, ?> config = builder.build()

        then: 'config should have the expected values'
        config
    }
}
