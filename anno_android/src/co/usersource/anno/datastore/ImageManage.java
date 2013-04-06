/**
 * 
 */
package co.usersource.anno.datastore;

import java.io.IOException;

import android.graphics.Bitmap;

/**
 * Interface for manipulate images.
 * 
 * @author topcircler
 * 
 */
public interface ImageManage {

  /**
   * Save the image to disk and return the key for this image.
   * 
   * @param bitmap
   * @return key of this saved image.
   */
  String saveImage(Bitmap bitmap) throws IOException;

  /**
   * Load a image by the specified key. If the key doesn't exist
   * 
   * @param key
   * @return
   */
  Bitmap loadImage(String key);

}
