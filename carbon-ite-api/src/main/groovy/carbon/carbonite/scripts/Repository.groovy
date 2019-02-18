package carbon.carbonite.scripts

import groovy.transform.CompileStatic

/**
 * Database repository for handling {@link Script} persistence
 *
 * @since 0.2.0
 */
@CompileStatic
interface Repository {

    /**
     * Lists latest status of the {@link Script} instances
     *
     * @return a list of the current running {@link Script} instances
     * @since 0.2.0
     */
    List<Script> list()
}
