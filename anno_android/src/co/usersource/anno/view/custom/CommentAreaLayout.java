/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import co.usersource.anno.R;

/**
 * @author topcircler
 * 
 */
public class CommentAreaLayout extends RelativeLayout {

  private CircleArrow circle;
  private EditTextLayout commentLayout;
  private EditText commentInput;
  private LinearLayout commentActionBar;
  private float boundary;
  private static final float DEFAULT_BOUNDARY = 10;
  private boolean circleOnTop = true;

  /**
   * @param context
   * @param attrs
   */
  public CommentAreaLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.CommentArea);
    boundary = a.getDimension(R.styleable.CommentArea_arrow_boundary,
        DEFAULT_BOUNDARY);

    a.recycle();
  }

  public void move(int x, int y) {
    if (circle == null) {
      circle = (CircleArrow) findViewById(R.id.circleArrow);
    }
    if (commentLayout == null) {
      commentLayout = (EditTextLayout) findViewById(R.id.input_area);
    }
    if (commentInput == null) {
      commentInput = (EditText) findViewById(R.id.etComment);
    }
    if (commentActionBar == null) {
      commentActionBar = (LinearLayout) findViewById(R.id.comment_action_bar);
    }

    RelativeLayout parent = (RelativeLayout) this.getParent();
    if (circleOnTop) {
      if (y > parent.getHeight() / 3) { // lower than 1/3, change circle to
                                        // bottom.
        flip(circleOnTop);
        circleOnTop = false;
      }
    } else {
      if (y < parent.getHeight() / 3) {
        flip(circleOnTop);
        circleOnTop = true;
      }
    }

    if (y + circle.getCircleRadius() < parent.getHeight()) {
      RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
          this.getWidth(), this.getHeight());
      if (circleOnTop) {
        lp.setMargins(0, y, 0, 0);
      } else {
        lp.setMargins(0, y - getHeight() + (int) circle.getCircleRadius(), 0, 0);
      }
      this.setLayoutParams(lp);
    }

    if (x + circle.getCircleRadius() * 2 < getWidth()) {
      float margin = this.getContext().getResources()
          .getDimension(R.dimen.comment_area_marginLeftRight);
      float arrowSpace = this.getContext().getResources()
          .getDimension(R.dimen.comment_arrow_space);
      circle.setCircleLeft(x);
      if (x < margin + boundary) {
        circle.setArrowLeft(margin + boundary);
        commentLayout.setArrowLeft(boundary);
      } else if (x > margin + commentLayout.getWidth() - boundary - arrowSpace) {
        circle.setArrowLeft(margin + commentLayout.getWidth() - boundary
            - arrowSpace);
        commentLayout.setArrowLeft(commentLayout.getWidth() - boundary
            - arrowSpace);
      } else {
        circle.setArrowLeft(x);
        commentLayout.setArrowLeft(x - margin);
      }
    }
    circle.invalidate();
    commentLayout.invalidate();
    commentActionBar.invalidate();
    invalidate();
  }

  private void flip(boolean direction) {
    RelativeLayout.LayoutParams circleLp = new RelativeLayout.LayoutParams(
        LayoutParams.MATCH_PARENT, (int) getContext().getResources()
            .getDimension(R.dimen.comment_indicate_height));
    RelativeLayout.LayoutParams abLp = new RelativeLayout.LayoutParams(
        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    int margin = (int) getContext().getResources().getDimension(
        R.dimen.comment_area_marginLeftRight);
    abLp.setMargins(margin, 0, margin, 0);
    if (direction) { // top to bottom
      circleLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
      circleLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
      abLp.addRule(RelativeLayout.ABOVE, R.id.circleArrow);
    } else { // bottom to top.
      circleLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
      circleLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
      abLp.addRule(RelativeLayout.BELOW, R.id.circleArrow);
    }
    commentLayout.setArrowOnTop(!direction);
    circle.setArrowOnTop(!direction);
    circle.setLayoutParams(circleLp);
    commentActionBar.setLayoutParams(abLp);
  }
}
