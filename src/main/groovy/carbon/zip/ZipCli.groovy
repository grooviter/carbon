package carbon.zip

import org.zeroturnaround.zip.ZipUtil

/**
 * Utility class to zip files
 *
 * @since 0.1.0
 */
class ZipCli {

    /**
     * Creates a zip file with the directory passed as argument
     *
     * @param path path to zip
     * @param zipFile the target zip file
     * @since 0.1.0
     */
    void zip(final String path, final String zipFile) {
        ZipUtil.pack(new File(path), new File(zipFile))
    }

    /**
     * Unzips the path passed as argument to the destination file
     *
     * @param path path to unzip
     * @param destination target destination
     * @since 0.1.0
     */
    void unzip(final String path, final String destination) {
        ZipUtil.unpack(new File(path), new File(destination))
    }
}
