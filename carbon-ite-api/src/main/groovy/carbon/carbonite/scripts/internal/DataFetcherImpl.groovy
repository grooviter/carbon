package carbon.carbonite.scripts.internal


import carbon.carbonite.scripts.Repository
import carbon.carbonite.scripts.Script
import carbon.carbonite.scripts.DataFetcher
import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Responsible to handle queries regarding scripts
 *
 * @since 0.2.0
 */
@Singleton
@CompileStatic
class DataFetcherImpl implements DataFetcher {

    /**
     * Access to database
     *
     * @since 0.2.0
     */
    @Inject
    Repository repository

    @Override
    List<Script> runningScripts(DataFetchingEnvironment environment) {
        return repository.list()
    }
}
