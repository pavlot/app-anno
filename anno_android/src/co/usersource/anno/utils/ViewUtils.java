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
 * @author topcircler
 * 
 */
public class ViewUtils {

  public static void displayInfo(Context context, int resId) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
  }

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
}
