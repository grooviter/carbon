package carbon.carbonite.graphql

import graphql.schema.DataFetchingEnvironment
import graphql.schema.DataFetchingEnvironmentImpl

class TestUtils {

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
