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
@SuppressWarnings('AbstractClassWithoutAbstractMethod')
abstract class CarbonScript extends PicocliBaseScript {

    /**
     * Logger of type {@link Logger}
     *
     * @since 0.2.0
     */
    @Delegate
    @SuppressWarnings('LoggerWithWrongModifiers')
    Logger log = LoggerFactory.getLogger('CarbonScript')

    /**
     * Returns the full Carbon configuration
     *
     * @return the Carbon configuration
     * @since 0.2.0
     */
    abstract Map<String, ?> getCarbonConfig()
}
