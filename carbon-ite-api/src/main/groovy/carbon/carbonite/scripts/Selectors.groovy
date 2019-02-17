package carbon.carbonite.scripts

import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic

/**
 * @since 0.2.0
 */
@CompileStatic
class Selectors {

    /**
     * @since 0.2.0
     */
    static class ListScripts {
        Integer offset
        Integer max
    }

    /**
     * @param env
     * @return
     * @since 0.2.0
     */
    static ListScripts runningScripts(DataFetchingEnvironment env) {
        return new ListScripts(offset: 0, max: 10)
    }
}
