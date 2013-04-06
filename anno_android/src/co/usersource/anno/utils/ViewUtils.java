/**
 * 
 */
package co.usersource.anno.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import co.usersource.anno.R;

/**
 * View Utilities.
 * 
 * @author topcircler
 * 
 */
public final class ViewUtils {

  /**
   * Display information.
   * 
   * This is the universal entry of displaying information. Just need to change
   * this implementation if you want to change the way to display information.
   */
  public static void displayInfo(Context context, int resId) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
  }

  /**
   * Display error.
   * 
   * This is the universal entry of displaying errors. Just need to change this
   * implementation if you want to change the way to display information.
   * 
   * @param message
   *          message string.
   */
  public static void displayError(Context context, String message) {
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    };

    new AlertDialog.Builder(context).setTitle(R.string.erorr_title)
        .setMessage(message).setNeutralButton(android.R.string.ok, listener)
        .show();
  }

  /**
   * Display error.
   * 
   * This is the universal entry of displaying errors. Just need to change this
   * implementation if you want to change the way to display information.
   * 
   * @param resId
   *          message resourceId。
   */
  public static void displayError(Context context, int resId) {
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    };

    new AlertDialog.Builder(context).setTitle(R.string.erorr_title)
        .setMessage(resId).setNeutralButton(android.R.string.ok, listener)
        .show();
  }
}
