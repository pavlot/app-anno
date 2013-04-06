/**
 * 
 */
package co.usersource.anno.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.os.StatFs;
import co.usersource.anno.R;

/**
 * System Utilities.
 * 
 * @author topcircler
 * 
 */
public final class SystemUtils {

  /**
   * Check if SD card is mounted.
   * 
   * @return if SD card is mounted.
   */
  public static boolean isSDCardMounted() {
    return Environment.MEDIA_MOUNTED.equals(Environment
        .getExternalStorageState());
  }

  /**
   * Get SD Card free spaces(bytes). If SD card isn't mounted, return -1.
   * 
   * @return SD Card free spaces(bytes)
   */
  public static long getFreeSDCardSpace() {
    if (isSDCardMounted()) {
      return getDirectorySize(Environment.getExternalStorageDirectory());
    }
    return -1;
  }

  /**
   * Get a directory size(bytes).
   * 
   * @param file
   *          directory file.
   * @return directory size(bytes).
   */
  public static long getDirectorySize(File file) {
    StatFs stat = new StatFs(file.getPath());
    long blockSize = stat.getBlockSize();
    long availableBlocks = stat.getAvailableBlocks();
    return blockSize * availableBlocks;
  }

  /**
   * Make directories. If directory exists, do nothing.
   * 
   * @param context
   * @param path
   *          directory path to create.
   * @throws IOException
   *           if directory doesn't exist and fail to create.
   */
  public static void mkdirs(Context context, String path) throws IOException {
    Resources res = context.getResources();
    File pathFile = new File(path);
    if (!pathFile.exists()) {
      if (!pathFile.mkdirs()) {
        throw new IOException(res.getString(R.string.fail_create_directory,
            pathFile.getAbsolutePath()));
      }
    }
  }

}
