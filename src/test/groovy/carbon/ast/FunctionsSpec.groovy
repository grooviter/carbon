package carbon.ast

import spock.lang.Specification

import java.util.function.Predicate

/**
 * Tests regarding usage of {@link Functions} methods
 *
 * @since 0.1.0
 */
class FunctionsSpec extends Specification {

    void 'flatten nested optionals'() {
        given: 'a list of optionals of optionals'
        List<Optional<Optional<String>>> xs = [
            Optional.of(Optional.of('a')),
            Optional.of(Optional.of('b')),
            Optional.of(Optional.of('c'))
        ]

        when: 'flatten the nested relationship'
        def ys = xs
        .stream()
        .flatMap(Functions.&flatten)
        .collect()

        then: 'we should be able to flatten the structure'
        ys*.get() == ['a', 'b', 'c']
    }

    void 'negating a given function'() {
        given:
        Predicate<String> predicate = { String st -> st == "word" } as Predicate<String>

        expect:
        Functions.not(predicate).test(sample) == expectation

        where:
        sample  | expectation
        "word"  | false
        "vowel" | true
    }
}
