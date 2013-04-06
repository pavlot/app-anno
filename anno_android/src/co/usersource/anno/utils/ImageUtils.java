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
public final class ImageUtils {

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
   * Compress the given bitmap and return a compressed one.
   * 
   * @param bitmap
   *          original bitmap.
   * @return compressed bitmap.
   */
  public static Bitmap compressBitmap(Bitmap bitmap) {
    // TODO: implement compress bitmap.
    return bitmap;
  }

}
