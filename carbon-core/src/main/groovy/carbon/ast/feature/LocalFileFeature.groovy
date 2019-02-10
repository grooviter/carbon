package carbon.ast.feature

import carbon.ast.CarbonScript

import java.security.MessageDigest
import java.text.DecimalFormat

/**
 * Utility function to deal with local files
 *
 * @since 0.2.0
 */
@SuppressWarnings('UnusedMethodParameter')
class LocalFileFeature {

    private static final String[] UNITS = ['B', 'kB', 'MB', 'GB', 'TB'] as String[]
    private static final Double FACTOR = 1024

    /**
     * Creates a hash of a given file's content, using a specific algorithm
     *
     * @param script Carbon script
     * @param algorithm type of algorithm to hash the file MD5, SHA1
     * @param file file to create the hash from
     * @return an {@link String} with the hash of the file's content
     * @since 0.2.0
     */
    static String hash(CarbonScript script, String algorithm, File file) {
        return MessageDigest
            .getInstance(algorithm)
            .digest(file.bytes)
            .encodeHex()
            .toString()
    }

    /**
     * Access to Groovy {@link AntBuilder}
     *
     * @param script
     * @return an instance of {@link AntBuilder}
     * @since 0.2.0
     */
    static AntBuilder getAnt(CarbonScript script) {
        return new AntBuilder()
    }

    /**
     * Lists all files within a path
     *
     * @param script Carbon script
     * @param dir directory to list files from
     * @return an array of {@link File} type
     * @since 0.2.0
     */
    static File[] ls(CarbonScript script, String dir) {
        return new File(dir).listFiles()
    }

    /**
     * Lists all files within a path
     *
     * @param script Carbon script
     * @param dir directory to list files from
     * @return an array of {@link File} type
     * @since 0.2.0
     */
    static File[] ls(CarbonScript script, File dir) {
        return dir.listFiles()
    }

    /**
     * Shows the file size in a friendlier way
     *
     * @param script Carbon script
     * @param path file path
     * @return a string containing the file size
     * @see <a href="https://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc">Stackoverflow</a>
     * @since 0.2.0
     */
    static String fileSize(CarbonScript script, final String path) {
        return fileSize(script, new File(path))
    }

    /**
     * Shows the file size in a friendlier way
     *
     * @param script Carbon script
     * @param file file to get the size from
     * @return a string containing the file size
     * @see <a href="https://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc">Stackoverflow</a>
     * @since 0.2.0
     */
    @SuppressWarnings('DuplicateStringLiteral')
    static String fileSize(CarbonScript script, final File file) {
        long size = file.length()

        if (size <= 0) {
            return '0'
        }

        int digitGroups = (int) (Math.log10(size) / Math.log10(FACTOR))

        return new DecimalFormat('#,##0.#')
            .format(size / Math.pow(FACTOR, digitGroups)) + ' ' + UNITS[digitGroups]
    }
}
