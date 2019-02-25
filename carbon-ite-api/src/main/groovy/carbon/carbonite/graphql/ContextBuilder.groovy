package carbon.carbonite.graphql

import io.micronaut.http.HttpRequest

/**
 * Responsible for building a GraphQL context
 *
 * @since 0.2.0
 */
interface ContextBuilder {

    /**
     * Builds a GraphQL context from the information provided
     * in the http request
     *
     * @param httpRequest an instance of {@link HttpRequest}
     * @return an object that is going to be used as GraphQL context
     * @since 0.2.0
     */
    Object build(HttpRequest httpRequest)
}
