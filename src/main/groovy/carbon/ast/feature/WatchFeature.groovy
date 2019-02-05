package carbon.ast.feature

import carbon.ast.CarbonScript

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
     * @param sleep time to wait until next execution
     * @param block code to execute
     * @since 0.2.0
     */
    static void watch(CarbonScript script, Integer sleep, Closure<Void> block) {
        while (true) {
            block()
            Thread.sleep(sleep)
        }
    }

    /**
     * Executes a code block indefinitely every two seconds (2000ms)
     *
     * @param script the Carbon script
     * @param block code to execute
     * @since 0.2.0
     */
    static void watch(CarbonScript script, Closure<Void> block) {
        while (true) {
            block()
            Thread.sleep(2000)
        }
    }
}
