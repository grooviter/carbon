package carbon.sql

import groovy.sql.Sql
import groovy.transform.TupleConstructor

/**
 * Utility class to handle SQL connections
 *
 * @since 0.1.0
 */
@TupleConstructor
class SqlCli {

    Map config

    /**
     * Returns a configured instance of type {@link Sql} with configuration
     * under 'sql' key
     *
     * @return an instance of {@link Sql} with the settings found
     * in the configuration
     * @since 0.1.0
     */
    Sql getConnection() {
        return this.getConnection('sql')
    }

    /**
     * Returns a configured instance of type {@link Sql} with configuration
     * under the key specified
     *
     * @param key configuration key to find sql settings
     * @return a configured instance of type {@link Sql}
     * @since 0.1.0
     */
    Sql getConnection(final String key) {
        Map sqlConfig = config?.get(key)
        String driverClass = sqlConfig?.driverClass
        String url = sqlConfig?.url
        String username = sqlConfig?.username
        String password = sqlConfig?.password

        assert sqlConfig, "No SQL configuration found with key -- $key --"
        assert driverClass, 'No SQL driverClass property found'
        assert url, 'No SQL url property found'

        return Sql.newInstance(url, username, password, driverClass)
    }
}
