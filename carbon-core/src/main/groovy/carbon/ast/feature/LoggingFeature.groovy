package carbon.ast.feature

import carbon.ast.CarbonScript
import org.fusesource.jansi.Ansi

/**
 * Utils to complete logging functionality
 *
 * @since 0.2.0
 */
class LoggingFeature {

    /**
     * Function to avoid calling {@link Ansi#toString} method every time
     * we'd like to render an {@link Ansi} object.
     *
     * @param script Carbon script
     * @param ansi an instance of type {@link Ansi}
     * @since 0.2.0
     */
    static void info(CarbonScript script, Ansi ansi) {
        script.log.info(ansi.toString())
    }
}
