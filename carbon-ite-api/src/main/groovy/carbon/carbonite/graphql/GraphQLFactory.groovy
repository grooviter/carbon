package carbon.carbonite.graphql

import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory

import javax.inject.Singleton

/**
 * Factory which creates a singleton of type {@link GraphQL}
 *
 * @since 0.2.0
 */
@Factory
class GraphQLFactory {

    /**
     * With a given {@link GraphQLSchema} it creates an instance
     * of type {@link GraphQL} which will process all queries
     * against the schema
     *
     * @param schema the GraphQL schema configured
     * @return an instance of type {@link GraphQL}
     * @since 0.2.0
     */
    @Bean
    @Singleton
    GraphQL get(GraphQLSchema schema) {
        return GraphQL.newGraphQL(schema).build()
    }
}
