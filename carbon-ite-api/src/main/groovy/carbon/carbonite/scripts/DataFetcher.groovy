package carbon.carbonite.scripts

import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic

/**
 * Responsible to handle queries regarding scripts
 *
 * @since 0.2.0
 */
@CompileStatic
interface DataFetcher {

    /**
     * Returns the latest list of running scripts
     *
     * @param environment GraphQL {@link DataFetchingEnvironment} instance
     * @return a list of type {@link Script}
     * @since 0.2.0
     */
    List<Script> runningScripts(DataFetchingEnvironment environment)
}
