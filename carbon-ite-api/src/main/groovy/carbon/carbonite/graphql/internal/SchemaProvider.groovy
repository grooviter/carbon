package carbon.carbonite.graphql.internal

import carbon.carbonite.scripts.ScriptFetcher
import gql.DSL
import graphql.schema.GraphQLSchema

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Creates ONE single instance of type {@link GraphQLSchema}
 *
 * @since 0.2.0
 */
@Singleton
class SchemaProvider implements Provider<GraphQLSchema> {

    /**
     * Handles queries regarding {@link Script} instances
     *
     * @since 0.2.0
     */
    @Inject
    ScriptFetcher scriptFetcher

    @Override
    GraphQLSchema get() {
        return DSL.mergeSchemas {
            byResource('graphql/schema.graphql') {
                mapType('Queries') {
                    link('runningScripts', scriptFetcher.&runningScripts)
                }
            }
        }
    }
}
