package co.usersource.annoplugin.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.ActionBar;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import co.usersource.annoplugin.R;
import co.usersource.annoplugin.datastore.FileImageManage;
import co.usersource.annoplugin.datastore.ImageManage;
import co.usersource.annoplugin.datastore.TableCommentFeedbackAdapter;
import co.usersource.annoplugin.model.AnnoContentProvider;
import co.usersource.annoplugin.utils.AppConfig;
import co.usersource.annoplugin.utils.ImageUtils;
import co.usersource.annoplugin.utils.ViewUtils;
import co.usersource.annoplugin.view.custom.CommentAreaLayout;

/**
 * Edit feedback screen from share intent.
 * 
 * You can add comment for the chosen screenshot.
 * 
 * @author topcircler
 * 
 */
public class FeedbackEditActivity extends Activity {

  private static final String TAG = "FeedbackActivity";

  private ImageManage imageManage;
  private AsyncHandler handler;

  // view components.
  private CommentAreaLayout commentAreaLayout;
  private RelativeLayout imvScreenshot;
  private ActionBar actionBar;
  private EditText etComment;
  private Button btnComment;

  /**
   * token id represents inserting a comment in an async process.
   */
  private static final int TOKEN_INSERT_COMMENT = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feedback_edit_activity);

    AppConfig config = AppConfig.getInstance(this);
    imageManage = new FileImageManage(this, config);
    handler = new AsyncHandler(getContentResolver(), this);

    setComponents();
    handleIntent();
  }

  private void handleIntent() {
    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();

    if (Intent.ACTION_SEND.equals(action) && type != null) {
      if (type.startsWith("image/")) {
        handleFromShareImage(intent);
        Log.d(TAG, "package name:" + this.getPackageName());
      }
    }
  }

  private void setComponents() {
    imvScreenshot = (RelativeLayout) findViewById(R.id.imvScreenshot);
    etComment = (EditText) findViewById(R.id.etComment);
    btnComment = (Button) findViewById(R.id.btnComment);
    commentAreaLayout = (CommentAreaLayout) findViewById(R.id.commentArea);
    actionBar = getActionBar();

    btnComment.setOnClickListener(sendCommentClickListener);

    onComment();
  }

  private View.OnClickListener sendCommentClickListener = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      try {
        String comment = etComment.getText().toString();
        if (comment == null || comment.trim().isEmpty()) {
          ViewUtils.displayError(FeedbackEditActivity.this,
              R.string.invalid_comment_empty);
          return;
        }
        Bitmap bitmap = ImageUtils.compressBitmap(ImageUtils
            .getBitmapFromImageView(imvScreenshot));
        String imageKey;
        imageKey = imageManage.saveImage(bitmap);
        float y = commentAreaLayout.getY();
        float x = commentAreaLayout.getCircleX();
        boolean circleOnTop = commentAreaLayout.circleOnTop();

        ContentValues values = new ContentValues();
        values.put(TableCommentFeedbackAdapter.COL_COMMENT, comment);
        values.put(TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY, imageKey);
        values.put(TableCommentFeedbackAdapter.COL_POSITION_X, x);
        values.put(TableCommentFeedbackAdapter.COL_POSITION_Y, y);
        values.put(TableCommentFeedbackAdapter.COL_DIRECTION, circleOnTop ? 0
            : 1);
        handler.startInsert(TOKEN_INSERT_COMMENT, null,
            AnnoContentProvider.COMMENT_PATH_URI, values);
      } catch (IOException e) {
        Log.e(TAG, e.getMessage());
        ViewUtils.displayError(FeedbackEditActivity.this, e.getMessage());
      } catch (Exception e) {
        // catch other exceptions, such as SQLException.
        if (e != null)
          Log.e(TAG, e.getMessage());
        ViewUtils.displayError(FeedbackEditActivity.this,
            R.string.fail_send_comment);
      }
    }
  };

  /**
   * Handle pressing 'comment' button.
   * 
   */
  private void onComment() {
    actionBar.hide();
    commentAreaLayout.setVisibility(View.VISIBLE);
  }

  private void handleFromShareImage(Intent intent) {
    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    if (imageUri != null) {
      ContentResolver rc = this.getContentResolver();
      BitmapDrawable drawable;
      try {
        drawable = new BitmapDrawable(getResources(),
            rc.openInputStream(imageUri));
        imvScreenshot.setBackgroundDrawable(drawable);
      } catch (FileNotFoundException e) {
        Log.e(TAG, e.getMessage(), e);
      }
    }
  }

  /**
   * Async handler for query manipulation.
   * 
   * @author topcircler
   * 
   */
  private static class AsyncHandler extends AsyncQueryHandler {

    private WeakReference<Activity> activityRef;

    public AsyncHandler(ContentResolver cr, Activity activity) {
      super(cr);
      this.activityRef = new WeakReference<Activity>(activity);
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
      super.onInsertComplete(token, cookie, uri);

      if (token == TOKEN_INSERT_COMMENT) {
        Log.d(TAG,
            "insert comment successfully. inserted uri:" + uri.toString());
        ViewUtils.displayInfo(activityRef.get(), R.string.success_send_comment);
        activityRef.get().finish();
      }
    }

  }

}
