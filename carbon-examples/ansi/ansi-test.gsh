#!/usr/bin/env groovy

@Grab('com.github.grooviter:carbon-core:0.2.0')
@Grab('org.slf4j:slf4j-simple:1.7.25')
import org.fusesource.jansi.AnsiRenderer

carbon = [
    name: "lines",
    description: "logging example"
]

println clear()                                                         // <1>

println ansi("@|underline,bg_yellow,fg_black  Carbon with ansi ... |@") // <2>

println ansi("@|bold,yellow A|@nsi -- @|bold,yellow T|@ext")            // <3>

println ansi($/
@|blink_slow    _____          ______                            __|@
@|blink_slow   / ___/____     / ____/________  ____ _   ____  __/ /|@
@|blink_slow   \__ \/ __ \   / / __/ ___/ __ \/ __ \ | / / / / / / |@
@|blink_slow  ___/ / /_/ /  / /_/ / /  / /_/ / /_/ / |/ / /_/ /_/  |@
@|blink_slow /____/\____/   \____/_/   \____/\____/|___/\__, (_)   |@
@|blink_slow                                           /____/      |@
/$)                                                                     // <4>
