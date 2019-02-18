package carbon.carbonite.graphql

import carbon.carbonite.scripts.internal.DataFetcherImpl as ScriptDataFetcher
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
    ScriptDataFetcher scriptDataFetcher

    @Override
    GraphQLSchema get() {
        return DSL.mergeSchemas {
            byResource('graphql/schema.graphql') {
                mapType('Queries') {
                    link('runningScripts', scriptDataFetcher.&runningScripts)
                }
            }
        }
    }
}
