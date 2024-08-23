package backend.tools.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Provides convenience methods for file access.
 *
 * @author Michael
 */
public final class FileReader {
    /**
     * The size of the buffer in bytes.
     */
    private static final int BUFFER_CAPACITY = 1024;

    /**
     * Constructor not to be used.
     */
    private FileReader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Reads the file with the given path and returns the byte array.
     *
     * @param path The path to the file.
     * @return the bytes of the file.
     * @throws FileNotFoundException In case the file could not be found
     * @throws IOException           In case the file could not be read or closed.
     */
    public static byte[] readFile(final String path) throws FileNotFoundException, IOException {
        ByteArrayOutputStream bos = null;
        FileInputStream fis = null;

        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[BUFFER_CAPACITY];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        return bos != null ? bos.toByteArray() : null;
    }
}
