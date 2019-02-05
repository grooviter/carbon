package carbon.ast.feature

import carbon.ast.CarbonScript
import org.fusesource.jansi.Ansi

/**
 * Provides {@link Ansi} console functionality in a Carbon script.
 *
 * @since 0.2.0
 */
class AnsiFeature {

    private static final Integer BEGINNING = 9999
    private static final Integer PADDING = 30
    private static final String PIPE = '|'

    /**
     * Creates an instance of a {@link Ansi} type
     *
     * @param script the Carbon script instance
     * @return an instance of type {@link Ansi}
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script) {
        return Ansi.ansi()
    }

    /**
     * @param script the Carbon script
     * @param message
     * @return an instance of type {@link Ansi}*
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script, String message) {
        return Ansi.ansi().render(message)
    }

    /**
     * @param script the Carbon script
     * @param rows
     * @return an instance of type {@link Ansi}**
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script, List<Map<String, ?>> rows) {
        Integer columns = rows.first().keySet().size()
        Set<String> knames = rows.first().keySet()
        String headers = knames*.center(PADDING).join(PIPE)
        String headersLine =  (1..columns).collect({ ('-' * PADDING) }).join('+')
        String dataRows = rows.collect { Map m ->
            String row = m
                    .keySet()
                    .collect { k -> m[k].toString().take(PADDING).padRight(PADDING) }.join(PIPE)

            return row
        }.join('\n')

        StringBuilder builder = new StringBuilder()
            .append(headers)
            .append('\n')
            .append(headersLine)
            .append('\n')
            .append(dataRows)

        return Ansi.ansi().render(builder.toString())
    }

    /**
     * @param script the Carbon script
     * @return an instance of type {@link Ansi}***
     * @since 0.2.0
     */
    static Ansi clear(CarbonScript script) {
        return Ansi
            .ansi()
            .cursorUp(BEGINNING)
            .cursorLeft(BEGINNING)
            .eraseScreen()
    }
}
