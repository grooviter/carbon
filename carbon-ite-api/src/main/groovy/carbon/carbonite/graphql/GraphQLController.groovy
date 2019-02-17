package carbon.carbonite.graphql

import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
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
     * @since 0.2.0
     */
    @Inject
    GraphQL graphQL

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
    ExecutionResult graphql(@Body GraphQLRequest request) {
        ExecutionInput input = ExecutionInput
            .newExecutionInput()
            .query(request.query)
            .context(null)
            .variables(request.variables)
            .build()

        return graphQL.execute(input)
    }
}
