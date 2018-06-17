package carbon.ast;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Set of function to deal with plain functions
 *
 * @since 0.1.0
 */
public class Functions {

    /**
     * Flattens a given {@link Optional} nested object
     *
     * @param wrapped the nested value
     * @return an unwrapped nested {@link Optional}
     * @since 0.1.0
     */
    public static <T> Stream<T> flatten(Optional<T> wrapped) {
        return wrapped.map(Stream::of).orElseGet(Stream::empty);
    }

    /**
     * Negates a given {@link Predicate}
     *
     * @param predicate predicate to negate
     * @return the same predicate with opposite meaning
     * @since 0.1.0
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}
