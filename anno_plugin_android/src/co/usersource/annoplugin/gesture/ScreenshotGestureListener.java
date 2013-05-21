/**
 * 
 */
package co.usersource.annoplugin.gesture;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.net.Uri;
import android.util.Log;

/**
 * Screenshot gesture listener, detect and process spiral gesture.
 * 
 * @author topcircler
 * 
 */
public class ScreenshotGestureListener implements OnGesturePerformedListener {

  private static final String TAG = "ScreenshotGestureListener";
  private static final String GESTURE_NAME = "UserSource spiral";
  private Activity activity;
  private GestureLibrary gestureLibrary = null;

  private String mockPath = "/sdcard/Pictures/Screenshots/Screenshot_2013-05-05-20-13-47.png";

  public ScreenshotGestureListener(Activity activity, int rawResourceId) {
    this.activity = activity;
    gestureLibrary = GestureLibraries.fromRawResource(activity, rawResourceId);
    gestureLibrary.load();
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.gesture.GestureOverlayView.OnGesturePerformedListener#
   * onGesturePerformed(android.gesture.GestureOverlayView,
   * android.gesture.Gesture)
   */
  @Override
  public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
    Log.d(TAG, "on gesture performed.");

    ArrayList<Prediction> prediction = gestureLibrary.recognize(gesture);
    if (prediction.size() > 0) {
      String name = prediction.get(0).name;
      Log.d(TAG, prediction.get(0).name);
      if (GESTURE_NAME.equalsIgnoreCase(name)) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("co.usersource.doui",
            "co.usersource.annoplugin.view.FeedbackEditActivity");
        intent.setType("image/*");
        File imageFile = new File(mockPath);
        Uri imageUri = Uri.parse("file://" + imageFile.getPath());
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        activity.startActivity(intent);
      }
    } else {
      Log.d(TAG, "no match.");
    }
  }

}
