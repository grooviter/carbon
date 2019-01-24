package carbon

import groovy.transform.TupleConstructor
import carbon.zip.ZipCli
import carbon.sql.SqlCli
import carbon.log.LoggerCli

/**
 * Represents the entry point to a set of general utilities
 *
 * @since 0.1.0
 */
@TupleConstructor
class ConfiguredCli {

    Map config

    /**
     * Returns a {@link SqlCli} instance to handle SQL connections
     *
     * @return a {@link SqlCli} instance
     * @since 0.1.5
     */
    SqlCli sql() {
        return new SqlCli(config)
    }

    /**
     * Returns a {@link ZipCli} instance to handle zip files
     *
     * @return a {@link ZipCli} instance
     * @since 0.1.0
     */
    ZipCli zip() {
        return new ZipCli()
    }

    /**
     * Returns a {@link LoggerCli} instance to handle console log
     *
     * @return a {@link LoggerCli} instance
     * @since 0.1.4
     */
    LoggerCli log() {
        return new LoggerCli()
    }
}
