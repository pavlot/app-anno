/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import co.usersource.anno.utils.ViewUtils;

/**
 * @author topcircler
 * 
 */
public class EditTextLayout extends RelativeLayout {

  Paint paint;
  Path path;

  /**
   * @param context
   * @param attrs
   */
  public EditTextLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.setWillNotDraw(false);

    paint = new Paint();
    path = new Path();
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.view.ViewGroup#dispatchDraw(android.graphics.Canvas)
   */
  @Override
  protected void dispatchDraw(Canvas canvas) {

    int width = this.getWidth();
    int height = this.getHeight();
    int borderWidth = 6;

    paint.setStrokeWidth(1);
    path.reset();
    paint.setColor(getResources().getColor(
        co.usersource.anno.R.color.transparent_orange));
    path.moveTo(borderWidth, borderWidth);
    path.lineTo(width - borderWidth, 0);
    path.lineTo(width - borderWidth, height - borderWidth);
    path.lineTo(borderWidth, height - borderWidth);
    path.lineTo(borderWidth, borderWidth);
    canvas.drawPath(path, paint);
    path.close();

    paint.setStrokeWidth(borderWidth);
    canvas.drawLine(ViewUtils.dip2px(getContext(), 100), borderWidth / 2,
        ViewUtils.dip2px(getContext(), 140), borderWidth / 2, paint);
    paint.setColor(getResources().getColor(android.R.color.black));
    canvas.drawLine(0, borderWidth / 2, ViewUtils.dip2px(getContext(), 100)
        + borderWidth, borderWidth / 2, paint);
    canvas.drawLine(ViewUtils.dip2px(getContext(), 140) - borderWidth,
        borderWidth / 2, width, borderWidth / 2, paint);
    canvas.drawLine(width - borderWidth / 2, 0, width - borderWidth / 2,
        height, paint);
    canvas.drawLine(width, height - borderWidth / 2, 0, height - borderWidth
        / 2, paint);
    canvas.drawLine(borderWidth / 2, height, borderWidth / 2, 0, paint);
    super.dispatchDraw(canvas);
  }
}
