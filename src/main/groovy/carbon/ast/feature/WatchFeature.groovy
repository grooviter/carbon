package carbon.ast.feature

import carbon.ast.CarbonScript
import java.util.concurrent.TimeUnit

/**
 * Sometimes you need to execute certain tasks repeatedly every
 * some few seconds.
 *
 * @since 0.2.0
 */
@SuppressWarnings('UnusedMethodParameter')
class WatchFeature {

    /**
     * Executes a code block indefinitely every certain milliseconds
     *
     * @param script the Carbon script
     * @param timeout time to wait until next execution
     * @param unit type of units for the timeout
     * @param block code to execute
     * @since 0.2.0
     */
    static void watch(CarbonScript script, Integer timeout, TimeUnit unit, Closure<Void> block) {
        while (true) {
            block()
            unit.sleep(timeout)
        }
    }

    /**
     * Executes a code block indefinitely every two seconds
     *
     * @param script the Carbon script
     * @param block code to execute
     * @since 0.2.0
     */
    static void watch(CarbonScript script, Closure<Void> block) {
        while (true) {
            block()
            TimeUnit.SECONDS.sleep(2)
        }
    }
}
