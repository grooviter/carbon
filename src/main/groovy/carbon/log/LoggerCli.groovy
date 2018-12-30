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

    LoggerCli clear() {
        Ansi ansi = ansi()
            .cursorUp(BEGINNING)
            .cursorLeft(BEGINNING)
            .eraseScreen()

        print(ansi)
        return this
    }
}
