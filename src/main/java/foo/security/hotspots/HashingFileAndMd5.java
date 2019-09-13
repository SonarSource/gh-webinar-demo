package foo.security.hotspots;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

public final class HashingFileAndMd5 {
    private final File file;
    private final String md5;

    public HashingFileAndMd5(File file) {
        try (InputStream fis = FileUtils.openInputStream(file)) {
            this.file = file;
            this.md5 = DigestUtils.md5Hex(fis);
        } catch (IOException e) {
            throw new IllegalStateException("Fail to compute md5 of " + file, e);
        }
    }

    public File getFile() {
        return file;
    }

    public String getMd5() {
        return md5;
    }
}