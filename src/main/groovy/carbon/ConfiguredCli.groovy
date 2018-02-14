package carbon

import groovy.sql.Sql
import groovy.transform.TupleConstructor
import carbon.zip.ZipCli

/**
 *
 * @since 0.1.0
 */
@TupleConstructor
class ConfiguredCli {

    Map config

    /**
     * Returns a configured instance of type {@link Sql} with configuration
     * under 'sql' key
     *
     * @return an instance of {@link Sql} with the settings found
     * in the configuration
     * @since 0.1.0
     */
    Sql sql() {
        return sql('sql')
    }

    /**
     * Returns a configured instance of type {@link Sql} with configuration
     * under the key specified
     *
     * @param key configuration key to find sql settings
     * @return a configured instance of type {@link Sql}
     * @since 0.1.0
     */
    Sql sql(final String key) {
        Map sqlConfig = config?.get(key)
        String driverClass = sqlConfig?.driverClass
        String url = sqlConfig?.url
        String username = sqlConfig?.username
        String password = sqlConfig?.password

        assert sqlConfig, "No SQL configuration found with key -- $key --"
        assert driverClass, "No SQL driverClass property found"
        assert url, "No SQL url property found"

        return Sql.newInstance(url, username, password, driverClass)
    }

    /**
     * @return
     * @since 0.1.0
     */
    ZipCli zip() {
        return new ZipCli()
    }
}
