package carbon.ast.feature

import carbon.ast.CarbonScript
import org.apache.tools.ant.DirectoryScanner

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import org.zeroturnaround.zip.ZipUtil

/**
 * Utility function to deal with local files
 *
 * @since 0.2.0
 */
class LocalFileFeature {

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
     * Deletes files matching the path pattern
     *
     * @param script Carbon script
     * @param pathPattern pattern to locate files to delete
     * @since 0.2.0
     */
    static void delete(CarbonScript script, String pathPattern) {
        parsePathPattern(pathPattern).each { String file ->
            Files.deleteIfExists(Paths.get(file))
        }
    }

    /**
     * Moves files matching the path pattern
     *
     * @param script Carbon script
     * @param fromPattern pattern to locate files to move
     * @param to place to move the files to
     * @since 0.2.0
     */
    static void mv(CarbonScript script, String fromPattern , String to) {
        parsePathPattern(fromPattern).each { String file ->
            Files.move(Paths.get(file), Paths.get(to), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    /**
     * Lists all files within a path
     *
     * @param script Carbon script
     * @param path exact path of a directory to list its files
     * @return an array of {@link File} type
     * @since 0.2.0
     */
    static File[] ls(CarbonScript script, String path) {
        return parsePathPattern(path).collect { new File(it) } as File[]
    }

    /**
     * Copies files matching path pattern
     *
     * @param script Carbon script
     * @param fromPattern pattern to locate files to copy
     * @param to place to copy files to
     * @since 0.2.0
     */
    static void cp(CarbonScript script, String fromPattern, String to) {
        parsePathPattern(fromPattern).each { String file ->
            Files.copy(Paths.get(file), Paths.get(to), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    /**
     * Creates a zip file with the directory passed as argument
     *
     * @param script Carbon script
     * @param path path to zip
     * @param zipFile the target zip file
     * @since 0.1.0
     */
    static void zip(CarbonScript script, final String path, final String zipFile) {
        ZipUtil.pack(new File(path), new File(zipFile))
    }

    /**
     * Unzips the path passed as argument to the destination file
     *
     * @param script Carbon script
     * @param path path to unzip
     * @param destination target destination
     * @since 0.1.0
     */
    static void unzip(CarbonScript script, final String path, final String destination) {
        ZipUtil.unpack(new File(path), new File(destination))
    }

    private static String[] parsePathPattern(String patternPath) {
        DirectoryScanner scanner = new DirectoryScanner()
        scanner.setIncludes([patternPath] as String[])
        scanner.setCaseSensitive(true)
        scanner.scan()

        return scanner.includedFiles
    }
}
