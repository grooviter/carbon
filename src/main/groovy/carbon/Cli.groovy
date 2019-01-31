package carbon

/**
 * Entry point for Carbon
 *
 * @since 0.1.0
 */
class Cli {

    /**
     * Prepares the client with a given configuration file
     *
     * @param path where to find the yaml configuration file
     * @return a configured client. An instance of {@link ConfiguredCli}
     * @since 0.1.0
     */
    static ConfiguredCli withConfig(String path) {
        Map map = new ConfigSlurper().parse(new File(path).toURL()) as Map

        return new ConfiguredCli(map)
    }

    /**
     * Prepares the client with a given configuration file
     *
     * @return an empty configured client. An instance of {@link ConfiguredCli}
     * @since 0.1.0
     */
    static ConfiguredCli withConfig() {
        return new ConfiguredCli([:])
    }

    /**
     * Executes a code block indefinitely every certain milliseconds
     *
     * @param sleep time to wait until next execution
     * @param block code to execute
     * @since 0.1.5
     */
    static void watch(Integer sleep, Closure<Void> block) {
        while (true) {
            block()
            Thread.sleep(sleep)
        }
    }

    /**
     * Executes a code block indefinitely every two seconds (2000ms)
     *
     * @param block code to execute
     * @since 0.1.5
     */
    static void watch(Closure<Void> block) {
        while (true) {
            block()
            Thread.sleep(2000)
        }
    }
}
