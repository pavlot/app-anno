/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import co.usersource.anno.R;

/**
 * @author topcircler
 * 
 */
public class CommentAreaLayout extends RelativeLayout {

  private CircleArrow circle;
  private EditTextLayout commentLayout;
  private float boundary;
  private static final float DEFAULT_BOUNDARY = 10;

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

    RelativeLayout parent = (RelativeLayout) this.getParent();
    if (y + this.getHeight() < parent.getHeight()) {
      RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
          this.getWidth(), this.getHeight());
      lp.setMargins(0, y, 0, 0);
      this.setLayoutParams(lp);
    }

    // check if out of boundary.
    if (x + circle.getCircleRadius() * 2 < this.getWidth()) {
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
      circle.invalidate();
      commentLayout.invalidate();
    }
  }
}
