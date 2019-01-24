package carbon.log

import static org.fusesource.jansi.Ansi.ansi

import org.fusesource.jansi.Ansi
import groovy.transform.CompileStatic

/**
 *
 * Utility class to handle how to print out messages to console. These
 * messages are capable of use ansi colors.
 *
 * @since 0.1.5
 */
@CompileStatic
@SuppressWarnings('Println')
class LoggerCli {

    /**
     * Max number of lines or columns of the console
     *
     * @since 0.1.5
     */
    static final Integer BEGINNING = 9999

    /**
     * When logging table-like structures, the cell width
     *
     * @since 0.1.6
     */
    static final Integer PADDING = 30

    static final String PIPE = '|'

    /**
     * Prints the message passed as argument as argument
     *
     * @param message message to show
     * @return the current {@link LoggerCli} instance
     * @since 0.1.5
     */
    LoggerCli log(String message) {
        Ansi ansi = ansi().render(message)

        print(ansi)
        return this
    }

    /**
     * Prints an empty new line
     *
     * @return the current {@link LoggerCli} instance
     * @since 0.1.5
     */
    LoggerCli logln() {
        Ansi ansi = ansi().render('')

        println(ansi)
        return this
    }

    /**
     * Prints a new line with the message passed as argument as
     * argument
     *
     * @param message message to show
     * @return the current {@link LoggerCli} instance
     * @since 0.1.5
     */
    LoggerCli logln(String message) {
        Ansi ansi = ansi().render(message)

        println(ansi)
        return this
    }

    /**
     * Prints a new line with the list of rows passed as argument
     *
     * @param rows list of {@link Map} instances
     * @return the current {@link LoggerCli} instance
     * @since 0.1.5
     */
    LoggerCli logln(List<Map<String, ?>> rows) {
        Integer columns = rows.first().keySet().size()
        Set<String> knames = rows.first().keySet()
        String headers = knames*.center(PADDING).join(PIPE)
        String headersLine =  (1..columns).collect({ ('-' * PADDING) }).join('+')

        logln(headers)
        logln(headersLine)

        rows.each { Map m ->
            String row = m
                .keySet()
                .collect { k -> m[k].toString().take(PADDING).padRight(PADDING) }.join(PIPE)

            logln(row)
        }

        return this
    }

    /**
     * Clears the console
     *
     * @return the current {@link LoggerCli} instance
     * @since 0.1.5
     */
    LoggerCli clear() {
        Ansi ansi = ansi()
            .cursorUp(BEGINNING)
            .cursorLeft(BEGINNING)
            .eraseScreen()

        print(ansi)
        return this
    }
}
