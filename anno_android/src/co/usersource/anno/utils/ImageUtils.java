/**
 * 
 */
package co.usersource.anno.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Image Utilities.
 * 
 * @author topcircler
 * 
 */
public class ImageUtils {

  /**
   * Get bitmap from image view.
   * 
   * @param imageView
   *          imageView.
   * @return bitmap.
   */
  public static Bitmap getBitmapFromImageView(ImageView imageView) {
    imageView.setDrawingCacheEnabled(true);
    Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
    imageView.setDrawingCacheEnabled(false);
    return bitmap;
  }

  /**
   * Compress image.
   */
  public static Bitmap compressBitmap(Bitmap bitmap) {
    // TODO:
    return bitmap;
  }

}
