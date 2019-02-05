package carbon.ast.feature

import carbon.ast.CarbonScript
import org.fusesource.jansi.Ansi

/**
 * Provides {@link Ansi} console functionality in a Carbon script.
 *
 * @since 0.2.0
 */
@SuppressWarnings('UnusedMethodParameter')
class AnsiFeature {

    private static final Integer BEGINNING = 9999
    private static final Integer PADDING = 30
    private static final String PIPE = '|'
    private static final String NEW_LINE = '\n'

    /**
     * Creates an instance of an {@link Ansi} type
     *
     * @param script the Carbon script instance
     * @return an instance of type {@link Ansi}
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script) {
        return Ansi.ansi()
    }

    /**
     * Creates an instance of an {@link Ansi} type
     *
     * @param script the Carbon script
     * @param message
     * @return an instance of type {@link Ansi}*
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script, String message) {
        return Ansi.ansi().render(message)
    }

    /**
     * Creates the output for a tabular-like data. It resembles
     * the output from some database console clients.
     *
     * @param script the Carbon script
     * @param rows the tabular data
     * @return an instance of type {@link Ansi}
     * @since 0.2.0
     */
    static Ansi ansi(CarbonScript script, List<Map<String, ?>> rows) {
        Integer columns = rows.first().keySet().size()
        Set<String> columnNames = rows.first().keySet()
        String headers = columnNames*.center(PADDING).join(PIPE)

        String headersLine =  (1..columns)
            .collect({ '-' * PADDING })
            .join('+')

        String dataRows = rows
            .collect(AnsiFeature.&processRow)
            .join(NEW_LINE)

        StringBuilder builder = new StringBuilder()
            .append(headers)
            .append(NEW_LINE)
            .append(headersLine)
            .append(NEW_LINE)
            .append(dataRows)

        return Ansi.ansi().render(builder.toString())
    }

    /**
     * Creates the output needed to clear the console
     *
     * @param script the Carbon script
     * @return an instance of type {@link Ansi}
     * @since 0.2.0
     */
    static Ansi clear(CarbonScript script) {
        return Ansi
            .ansi()
            .cursorUp(BEGINNING)
            .cursorLeft(BEGINNING)
            .eraseScreen()
    }

    private String processRow(Map<String,?> rowToProcess) {
        return rowToProcess
                .keySet()
                .collect { k -> rowToProcess[k].toString().take(PADDING).padRight(PADDING) }
                .join(PIPE)
    }
}
