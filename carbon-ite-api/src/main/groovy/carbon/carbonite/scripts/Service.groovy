package carbon.carbonite.scripts

import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Service responsible to handle queries regarding scripts
 *
 * @since 0.2.0
 */
@Singleton
@CompileStatic
class Service {

    /**
     * Access to database
     *
     * @since 0.2.0
     */
    @Inject
    JdbcRepository repository

    /**
     * Returns the latest list of running scripts
     *
     * @param environment
     * @return a list of type {@link Script}
     * @since 0.2.0
     */
    List<Script> runningScripts(DataFetchingEnvironment environment) {
        return repository.list()
    }
}
