/**
 * 
 */
package co.usersource.anno.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author topcircler
 * 
 */
public class IOUtils {

  public static byte[] inputStreamToByte(InputStream is) throws IOException {
    ByteArrayOutputStream bytestream = null;
    try {
      bytestream = new ByteArrayOutputStream();
      int ch;
      while ((ch = is.read()) != -1) {
        bytestream.write(ch);
      }
      byte imgdata[] = bytestream.toByteArray();
      return imgdata;
    } finally {
      if (bytestream != null)
        bytestream.close();
    }
  }

}
