/**
 * 
 */
package co.usersource.annoplugin;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import co.usersource.annoplugin.gesture.ScreenshotGestureListener;

/**
 * Anno plugin entry.
 * 
 * @author topcircler
 * 
 */
public class AnnoPlugin {

  /**
   * Enable taking screenshot by certain gesture.
   * 
   * @param activity
   * @param gestureViewId
   */
  public static void supportGesture(Activity activity, int gestureViewId) {
    GestureOverlayView gestureOverlayView = (GestureOverlayView) activity
        .findViewById(gestureViewId);
    gestureOverlayView.setGestureVisible(false);
    ScreenshotGestureListener gesturePerformedListener = new ScreenshotGestureListener(
        activity, R.raw.gestures);
    gestureOverlayView.addOnGesturePerformedListener(gesturePerformedListener);
  }

  public static void supportHotkey() {
    // TODO:
  }

}
