package co.usersource.anno.presenter;

import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import co.usersource.anno.view.FeedbackViewInterface;
import co.usersource.anno.view.viewobject.CommentFeedback;
import co.usersource.anno.view.viewobject.Feedback;

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

  public void sendFeedback(Bitmap screenshot, List<Feedback> feedbackList) {
    Log.d(TAG, "send feedback.");
    for (Feedback feedback : feedbackList) {
      if (feedback instanceof CommentFeedback) {
        // TODO: insert comment feedback into database.
      }
    }
    // TODO: call view to return to home screen.
  }

}
