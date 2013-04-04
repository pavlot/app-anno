package co.usersource.anno.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import co.usersource.anno.R;
import co.usersource.anno.presenter.FeedbackPresenter;
import co.usersource.anno.view.viewobject.CommentFeedback;
import co.usersource.anno.view.viewobject.FeedbackItem;

public class FeedbackActivity extends Activity implements FeedbackViewInterface {

  private static final String TAG = "FeedbackActivity";

  private FeedbackPresenter presenter;

  private ImageView imvScreenshot;
  private ActionBar actionBar;

  private EditText etComment;
  private Button btnComment;

  public FeedbackActivity() {
    presenter = new FeedbackPresenter(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.feedback_activity);

    setComponents();

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
        String comment = etComment.getText().toString();
        List<FeedbackItem> feedbackList = new ArrayList<FeedbackItem>();
        // TODO: get and set circle position.
        feedbackList.add(new CommentFeedback(comment, 0, 0));
//        imvScreenshot.getd
//        presenter.sendFeedback(feedbackList);
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
}
