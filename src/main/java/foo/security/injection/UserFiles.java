package foo.security.injection;

import java.util.Arrays;
import java.util.List;

/**
 * UserFiles
 */
public class UserFiles {

  public List<String> getAllFiles() {
    return Arrays.asList("all", "files");
  }

  public void remove(String file) {
  }
}