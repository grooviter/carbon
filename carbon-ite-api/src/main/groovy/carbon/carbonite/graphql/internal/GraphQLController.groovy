package carbon.carbonite.graphql.internal

import carbon.carbonite.graphql.ContextBuilder
import carbon.carbonite.graphql.GraphQLRequest
import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces

import javax.inject.Inject

/**
 * GraphQL entry point
 *
 * @since 0.2.0
 */
@Controller('/graphql')
class GraphQLController {

    /**
     * Contains the GraphQL schema
     *
     * @since 0.2.0
     */
    @Inject
    GraphQL graphQL

    /**
     * Builds a GraphQL context from the information contained in the
     * request
     *
     * @since 0.2.0
     */
    @Inject
    ContextBuilder contextBuilder

    /**
     * Enables a GraphQL endpoint which operates
     * under the HTTP POST verb
     *
     * @return a map containing the requested data
     * @since 0.2.0
     */
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ExecutionResult graphql(@Body GraphQLRequest request, HttpRequest httpRequest) {
        Object context = contextBuilder.build(httpRequest)
        ExecutionInput input = ExecutionInput
            .newExecutionInput()
            .query(request.query)
            .context(context)
            .variables(request.variables)
            .build()

        return graphQL.execute(input)
    }
}
