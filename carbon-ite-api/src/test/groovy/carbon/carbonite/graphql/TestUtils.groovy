package carbon.carbonite.graphql

import graphql.schema.DataFetchingEnvironment
import graphql.schema.DataFetchingEnvironmentImpl

/**
 * Util functions to deal with GraphQL
 *
 * @since 0.2.0
 */
class TestUtils {

    /**
     * Simulates that the current {@link DataFetchingEnvironment} has the expected
     * arguments
     *
     * @param arguments arguments coming from query
     * @return an instance of {@link DataFetchingEnvironment} with the desired arguments
     * @since 0.2.0
     */
    static DataFetchingEnvironment environmentWithArgs(Map<String, ?> arguments) {
        return new DataFetchingEnvironmentImpl(
                null,
                arguments,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
    }
}
