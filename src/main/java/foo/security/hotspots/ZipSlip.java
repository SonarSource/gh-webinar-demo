package foo.security.hotspots;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipSlip {

  public static void sanitizeAgainstZipFlipVulnerability(String fileName, String canonicalDestPath, String canonicalDirPath) throws ArchiverException {
    if (fileName.indexOf("..") != -1 && !canonicalDestPath.startsWith(canonicalDirPath + File.separator)) { // Sanitizer
      throw new ArchiverException("The file " + fileName + " is trying to leave the target output directory. Ignoring this file.");
    }
  }

  public static void safe_unzip(String zipFileName, File outputDir) throws ArchiverException, IOException {
    try (java.util.zip.ZipFile zipFile = new ZipFile(zipFileName)) {
      Enumeration<? extends ZipEntry> entries = zipFile.entries();
      while (entries.hasMoreElements()) {
        ZipEntry e = entries.nextElement(); // Sensitive
        File f = new File(outputDir, e.getName());
        InputStream input = zipFile.getInputStream(e);

        String canonicalDirPath = outputDir.getCanonicalPath();
        String canonicalDestPath = f.getCanonicalPath();
        
        sanitizeAgainstZipFlipVulnerability(e.getName(), canonicalDestPath, canonicalDirPath);

        extractFile(input, outputDir, e.getName());
      }
    }
  }

  public void unsafe_unzip(String zipFileName, File outputDir) throws ArchiverException, IOException {
    try (java.util.zip.ZipFile zipFile = new ZipFile(zipFileName)) {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
          ZipEntry e = entries.nextElement();
          File f = new File(outputDir, e.getName()); // Source
          InputStream input = zipFile.getInputStream(e);
    
          // no sanitization at all
    
          extractFile(input, outputDir, e.getName());
        }
    }
  }

  private static void extractFile(final InputStream in, final File outdir, final String name) throws IOException {
    final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir, name))); // Noncompliant
    byte[] buffer = new byte[1024];
    int len;
    while ((len = in.read(buffer)) != -1) {
      out.write(buffer, 0, len);
    }
  }
}