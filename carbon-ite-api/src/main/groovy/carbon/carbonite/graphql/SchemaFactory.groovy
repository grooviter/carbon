package carbon.carbonite.graphql

import gql.DSL
import graphql.schema.GraphQLSchema
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory

import javax.inject.Singleton

/**
 * Creates ONE single instance of type {@link GraphQLSchema}
 *
 * @since 0.2.0
 */
@Factory
class SchemaFactory {

    /**
     * Configures the GraphQL schema served by the api
     *
     * @return a configured instance of type {@link GraphQLSchema}
     * @since 0.2.0
     */
    @Bean
    @Singleton
    GraphQLSchema get() {
        return DSL.mergeSchemas {
            byResource('graphql/schema.graphql') {
                mapType('Queries') {
                    link('runningScripts') {
                        return [
                                [
                                name       : 'nasdaq-100',
                                description: 'Reads NASDAQ-100 stock values',
                                status     : 'RUNNING'
                                ]
                        ]
                    }
                }
            }
        }
    }
}
