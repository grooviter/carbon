package carbon.log

import static org.fusesource.jansi.Ansi.ansi
import static org.fusesource.jansi.Ansi.Erase

import org.fusesource.jansi.Ansi

class LoggerCli {

    static final Integer BEGINNING = 9999

    LoggerCli log(String message) {
        Ansi ansi = ansi().render(message)

        print(ansi)
        return this
    }

    LoggerCli logln(String message) {
        Ansi ansi = ansi().render(message)

        println(ansi)
        return this
    }

    @groovy.transform.CompileStatic
    LoggerCli table(List<Map> rows) {
        Integer columns = rows.first().keySet().size()
        Set<String> knames = rows.first().keySet()
        String headers = knames.collect({ it.center(30) }).join('|')
        String headersLine =  (1..columns).collect({ ('-' * 30) }).join('+')

        logln(headers)
        logln(headersLine)

        rows.each { Map m ->
            String row = m
                .keySet()
                .collect({ k ->
                    m[k].toString().take(30).padRight(30)
                }).join('|')

            logln(row)
        }

        return this
    }

    LoggerCli clear() {
        Ansi ansi = ansi()
            .cursorUp(BEGINNING)
            .cursorLeft(BEGINNING)
            .eraseScreen()

        print(ansi)
        return this
    }
}
