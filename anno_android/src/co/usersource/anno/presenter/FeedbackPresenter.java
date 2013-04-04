package co.usersource.anno.presenter;

import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import co.usersource.anno.view.FeedbackViewInterface;
import co.usersource.anno.view.viewobject.CommentFeedback;
import co.usersource.anno.view.viewobject.FeedbackItem;

/**
 * Presenter class for feedback activity, interact with feedback views and
 * models.
 * 
 * @author Leo
 * 
 */
public class FeedbackPresenter {

  private static final String TAG = "FeedbackPresenter";

  private FeedbackViewInterface view;

  public FeedbackPresenter(FeedbackViewInterface view) {
    this.view = view;
  }

  public void sendFeedback(Bitmap screenshot, List<FeedbackItem> feedbackList) {
    Log.d(TAG, "send feedback.");
    for (FeedbackItem feedback : feedbackList) {
      if (feedback instanceof CommentFeedback) {
        // TODO:
      }
    }
  }

}
