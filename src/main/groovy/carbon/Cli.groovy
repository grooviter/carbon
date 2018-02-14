package carbon

import org.yaml.snakeyaml.Yaml

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
        final Yaml snakeYaml = new Yaml()
        final Map map = snakeYaml.load(new FileReader(path)) as Map

        return new ConfiguredCli(map)
    }

    static ConfiguredCli withConfig() {
        return new ConfiguredCli([:])
    }
}
