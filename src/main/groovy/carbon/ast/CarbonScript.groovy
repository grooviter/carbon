package carbon.ast

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import picocli.groovy.PicocliBaseScript

/**
 * Base class for Carbon's scripts. The way of adding more
 * features to Carbon scripts is to create a ExtensionModule
 * targeting CarbonScript class.
 *
 * @since 0.2.0
 */
abstract class CarbonScript extends PicocliBaseScript {

    /**
     * Logger of type {@link Logger}
     *
     * @since 0.2.0
     */
    @Delegate
    Logger log = LoggerFactory.getLogger('CarbonScript')

    /**
     * @since 0.2.0
     */
    Map<String, ?> carbonConfig
}
