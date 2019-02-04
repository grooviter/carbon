package carbon.ast.feature

import carbon.ast.CarbonScript
import java.security.MessageDigest
import org.zeroturnaround.zip.ZipUtil

/**
 * @since 0.2.0
 */
class FileFeature {

    /**
     * @param script
     * @param algorithm
     * @param file
     * @return
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
     * Creates a zip file with the directory passed as argument
     *
     * @param script
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
     * @param script
     * @param path path to unzip
     * @param destination target destination
     * @since 0.1.0
     */
    static void unzip(CarbonScript script, final String path, final String destination) {
        ZipUtil.unpack(new File(path), new File(destination))
    }
}
