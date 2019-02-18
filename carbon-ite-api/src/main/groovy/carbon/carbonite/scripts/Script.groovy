package carbon.carbonite.scripts

import groovy.transform.CompileStatic
import groovy.transform.Immutable

/**
 * Represents the information about a given script such as
 * the id, name, status...
 *
 * @since 0.2.0
 */
@Immutable
@CompileStatic
class Script {

    /**
     * Unique identifier of the script in the system
     *
     * @since 0.2.0
     */
    UUID uuid

    /**
     * Name of the script
     *
     * @since 0.2.0
     */
    String name

    /**
     * Some description of the script
     *
     * @since 0.2.0
     */
    String description

    /**
     * Latest status of the script
     *
     * @since 0.2.0
     */
    String status
}
