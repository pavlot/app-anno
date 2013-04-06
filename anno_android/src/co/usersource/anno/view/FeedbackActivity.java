package co.usersource.anno.view;

import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import co.usersource.anno.R;
import co.usersource.anno.datastore.FileImageManage;
import co.usersource.anno.datastore.ImageManage;
import co.usersource.anno.datastore.TableCommentFeedbackAdapter;
import co.usersource.anno.model.AnnoContentProvider;
import co.usersource.anno.utils.ImageUtils;
import co.usersource.anno.utils.ViewUtils;

public class FeedbackActivity extends Activity {

  private static final String TAG = "FeedbackActivity";

  private ImageManage imageManage;
  private AsyncHandler handler;

  // components.
  private ImageView imvScreenshot;
  private ActionBar actionBar;
  private EditText etComment;
  private Button btnComment;

  private static final int TOKEN_INSERT_COMMENT = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feedback_activity);

    imageManage = new FileImageManage(this);
    handler = new AsyncHandler(getContentResolver(), getApplicationContext());

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
      }
    }
  }

  private void setComponents() {
    imvScreenshot = (ImageView) findViewById(R.id.imvScreenshot);
    etComment = (EditText) findViewById(R.id.etComment);
    btnComment = (Button) findViewById(R.id.btnComment);
    btnComment.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View view) {
        try {
          String comment = etComment.getText().toString();
          Bitmap bitmap = ImageUtils.compressBitmap(ImageUtils
              .getBitmapFromImageView(imvScreenshot));
          String imageKey;
          imageKey = imageManage.saveImage(bitmap);

          ContentValues values = new ContentValues();
          values.put(TableCommentFeedbackAdapter.COL_COMMENT, comment);
          values.put(TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY, imageKey);
          handler.startInsert(TOKEN_INSERT_COMMENT, null,
              AnnoContentProvider.COMMENT_PATH_URI, values);
        } catch (IOException e) {
          Log.e(TAG, e.getMessage());
          ViewUtils.displayError(FeedbackActivity.this, e.getMessage());
        }
      }

    });
    actionBar = getActionBar();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.feedback_action_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.feedback_action_comment:
      actionBar.hide();
      etComment.setVisibility(View.VISIBLE);
      btnComment.setVisibility(View.VISIBLE);
      return true;
    case R.id.feedback_action_draw:
    case R.id.feedback_action_audio:
      break;
    }
    return false;
  }

  private void handleFromShareImage(Intent intent) {
    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    if (imageUri != null) {
      imvScreenshot.setImageURI(imageUri);
    }
  }

  private static class AsyncHandler extends AsyncQueryHandler {

    private Context context;

    public AsyncHandler(ContentResolver cr, Context context) {
      super(cr);
      this.context = context;
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
      super.onInsertComplete(token, cookie, uri);

      if (token == TOKEN_INSERT_COMMENT) {
        Log.d(TAG,
            "insert comment successfully. inserted uri:" + uri.toString());
        ViewUtils.displayInfo(context, R.string.success_send_comment);
      }
    }

  };
}
