package carbon.ast.feature

import carbon.ast.CarbonScript
import groovy.sql.Sql

/**
 * Provides the creation of instances of type {@link Sql} from
 * the Carbon's configuration
 *
 * @since 0.2.0
 */
class SqlFeature {

    private static final String DEFAULT_KEY = 'sql'

    /**
     * Creates a {@link Sql} instance from the information
     * found under the 'sql' key inside Carbon's configuration
     *
     * @param script Carbon script
     * @return an instance of type {@link Sql}
     * @since 0.2.0
     */
    static Sql sql(CarbonScript script) {
        return sql(script, DEFAULT_KEY)
    }

    /**
     * Creates a {@link Sql} instance from the information
     * found under the key passed as parameter
     *
     * @param script Carbon script
     * @param key the key inside the Carbon configuration where to find
     * the database configuration
     * @return an instance of type {@link Sql}
     * @since 0.2.0
     */
    static Sql sql(CarbonScript script, String key) {
        Map<String,?> sqlConfig = script.carbonConfig[key]

        if (!sqlConfig) {
            throw new IllegalStateException("There's no database configuration under key '$key'")
        }

        return Sql.newInstance(sqlConfig)
    }
}
