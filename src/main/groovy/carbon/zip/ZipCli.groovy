package carbon.zip

import org.zeroturnaround.zip.ZipUtil

/**
 *
 *
 * @since 0.1.0
 */
class ZipCli {

    /**
     * @param path
     * @param zipFile
     * @since 0.1.0
     */
    void zip(final String path, final String zipFile) {
        ZipUtil.pack(new File(path), new File(zipFile))
    }

    /**
     * @param path
     * @param destination
     * @since 0.1.0
     */
    void unzip(final String path, final String destination) {
        ZipUtil.unpack(new File(path), new File(destination))
    }
}
