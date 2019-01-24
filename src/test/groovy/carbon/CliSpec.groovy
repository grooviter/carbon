package carbon

import spock.lang.Specification

/**
 * Checks {@link Cli} class
 *
 * @since 0.1.0
 */
class CliSpec extends Specification {
    void "load configuration"() {
        setup: 'test sql configuration path'
        String configPath = 'src/test/resources/carbon/sql.yaml'

        when: 'asking for the configuration'
        Map<String,?> result = Cli.withConfig(configPath).config

        then: 'checking sql settings'
        with(result.sql) {
            url == 'jdbc:h2:~/test'
            driverClass == 'org.h2.Driver'
            username == 'sa'
            !password
        }
    }
}
