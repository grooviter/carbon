package carbon.carbonite.graphql

import groovy.transform.CompileStatic
import groovy.transform.Immutable

/**
 * Represents the data coming from a GraphQL request. It
 * holds just the query as well as the variables required
 * to resolve that query.
 *
 * @since 0.2.0
 */
@Immutable
@CompileStatic
class GraphQLRequest {

    /**
     * Variables needed to resolve the query properly
     *
     * @since 0.2.0
     */
    Map<String,?> variables

    /**
     * The query to execute against the GraphQL schema
     *
     * @since 0.2.0
     */
    String query
}
