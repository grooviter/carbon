package carbon.carbonite.graphql

import carbon.carbonite.scripts.Service as ScriptService
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

    @Inject
    ScriptService scriptService

    @Override
    GraphQLSchema get() {
        return DSL.mergeSchemas {
            byResource('graphql/schema.graphql') {
                mapType('Queries') {
                    link('runningScripts', scriptService.&runningScripts)
                }
            }
        }
    }
}
