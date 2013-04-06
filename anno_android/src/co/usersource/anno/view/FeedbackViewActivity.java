package co.usersource.anno.view;

import java.lang.ref.WeakReference;

import android.app.ActionBar;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import co.usersource.anno.R;
import co.usersource.anno.datastore.FileImageManage;
import co.usersource.anno.datastore.ImageManage;
import co.usersource.anno.datastore.TableCommentFeedbackAdapter;
import co.usersource.anno.model.AnnoContentProvider;

/**
 * View comment feedback.
 * 
 * You can view comment for the chosen comment.
 * 
 * @author topcircler
 * 
 */
public class FeedbackViewActivity extends Activity {

  private static final String TAG = "FeedbackViewActivity";

  private ImageManage imageManage;
  private AsyncHandler handler;

  // components.
  private ImageView imvScreenshot;
  private TextView tvComment;
  private ActionBar actionBar;

  /**
   * token id represents retrieving comment in an async process.
   */
  private static final int TOKEN_GET_COMMENT = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feedback_view_activity);

    imageManage = new FileImageManage(this);
    handler = new AsyncHandler(getContentResolver(), this);

    setComponents();
    handleIntent();
  }

  private void handleIntent() {
    Intent intent = getIntent();
    String[] projection = { TableCommentFeedbackAdapter.COL_ID,
        TableCommentFeedbackAdapter.COL_COMMENT,
        TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY };
    Uri uri = intent.getParcelableExtra(AnnoContentProvider.COMMENT_PATH);
    handler.startQuery(TOKEN_GET_COMMENT, null, uri, projection, null, null,
        null);
  }

  private void setComponents() {
    imvScreenshot = (ImageView) findViewById(R.id.imvScreenshotView);
    tvComment = (TextView) findViewById(R.id.tvComment);
    actionBar = getActionBar();
    actionBar.hide();
  }

  private static class AsyncHandler extends AsyncQueryHandler {

    private WeakReference<FeedbackViewActivity> activityRef;

    public AsyncHandler(ContentResolver cr, FeedbackViewActivity activity) {
      super(cr);
      activityRef = new WeakReference<FeedbackViewActivity>(activity);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
      super.onQueryComplete(token, cookie, cursor);

      FeedbackViewActivity activity = activityRef.get();
      if (token == TOKEN_GET_COMMENT) {
        if (cursor != null && cursor.moveToFirst()) {

          int idx = cursor
              .getColumnIndex(TableCommentFeedbackAdapter.COL_COMMENT);
          if (idx != -1) {
            String comment = cursor.getString(idx);
            activity.tvComment.setText(comment);
          }
          idx = cursor
              .getColumnIndex(TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY);
          if (idx != -1) {
            String imageKey = cursor.getString(idx);
            activity.imvScreenshot.setImageBitmap(activity.imageManage
                .loadImage(imageKey));
          }
        }
      }
      activity = null;
      if (!cursor.isClosed()) {
        cursor.close();
      }
    }
  };
}
