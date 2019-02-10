package carbon.ast.feature

import carbon.ast.CarbonScript

import java.util.stream.Stream

/**
 * Utils to handle the System.in content. It could be useful
 * when dealing with piped input. For example in piped commands:
 *
 * <pre><code class="shell">count_words.groovy < README.md</code></pre>
 *
 * @since 0.2.0
 */
@SuppressWarnings('UnusedMethodParameter')
class PipeFeature {

    /**
     * Converts the current System.in content in a {@link Stream} of {@link String}
     *
     * @param script Carbon script
     * @return a {@link Stream} of {@link String} containing the piped content
     * @since 0.2.0
     */
    static Stream<String> pipedIn(CarbonScript script) {
        return new BufferedReader(new InputStreamReader(System.in)).lines()
    }

    /**
     * Returns whether the current script has received piped content or not
     *
     * @param script Carbon script
     * @return true if there's a piped content, false otherwise
     * @since 0.2.0
     */
    static Boolean hasPipedIn(CarbonScript script) {
        return System.in.available() > 0
    }
}
