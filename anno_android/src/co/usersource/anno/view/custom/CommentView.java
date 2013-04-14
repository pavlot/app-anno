/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import co.usersource.anno.R;

/**
 * @author topcircler
 * 
 */
public class CommentView extends View {

  private Paint paint;
  private Path path;

  /**
   * @param context
   * @param attrs
   */
  public CommentView(Context context, AttributeSet attrs) {
    super(context, attrs);
    paint = new Paint();
    path = new Path();
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.view.View#onDraw(android.graphics.Canvas)
   */
  @Override
  protected void onDraw(Canvas canvas) {
    int width = this.getWidth();
    int height = this.getHeight();

    int start = 100;

    // paint.setColor(getResources().getColor(android.R.color.darker_gray));
    // canvas.drawRect(0, 0, width, height, paint);

    // draw circle
    // paint.setColor(getResources().getColor(android.R.color.darker_gray));
    // float outRadius = ViewUtils.dip2px(getContext(), 20);
    // canvas.drawCircle(start + outRadius, outRadius, outRadius, paint);
    // float innerRadius = outRadius - 5;
    // paint.setColor(getResources().getColor(R.color.transparent_orange));
    // canvas.drawCircle(start + outRadius, outRadius, innerRadius, paint);

    // draw triangle
    // paint.setColor(getResources().getColor(android.R.color.black));
    // path.moveTo(start + outRadius, outRadius);
    // path.lineTo(start + outRadius * 2, outRadius * 3);
    // path.lineTo(width, outRadius * 3);
    // path.lineTo(width, outRadius * 3 + ViewUtils.dip2px(getContext(), 40));
    // path.lineTo(0, outRadius * 3 + ViewUtils.dip2px(getContext(), 40));
    // path.lineTo(0, outRadius * 3);
    // path.lineTo(start, outRadius * 3);
    // path.lineTo(start + outRadius, outRadius);
    // canvas.drawPath(path, paint);
    // path.reset();

    // draw transparent triangle
    int borderWidth = 5;
    // paint.setColor(getResources().getColor(R.color.transparent_orange));
    // path.moveTo(start + outRadius, outRadius + borderWidth);
    // path.lineTo(start + outRadius * 2 - borderWidth, outRadius * 3
    // + borderWidth);
    // path.lineTo(width - borderWidth, outRadius * 3 + borderWidth);
    // path.lineTo(width - borderWidth,
    // outRadius * 3 + ViewUtils.dip2px(getContext(), 40) - borderWidth);
    // path.lineTo(borderWidth, outRadius * 3 + ViewUtils.dip2px(getContext(),
    // 40)
    // - borderWidth);
    // path.lineTo(borderWidth, outRadius * 3 + borderWidth);
    // path.lineTo(start + borderWidth, outRadius * 3 + borderWidth);
    // path.lineTo(start + outRadius, outRadius + borderWidth);
    // canvas.drawPath(path, paint);
    // path.close();
    paint.setColor(getResources().getColor(android.R.color.black));
    path.moveTo(0, 0);
    path.lineTo(width, 0);
    path.lineTo(width, height);
    path.lineTo(0, height);
    path.lineTo(0, 0);
    canvas.drawPath(path, paint);

    path.reset();
    paint.setColor(getResources().getColor(R.color.transparent_orange));
    path.moveTo(borderWidth, borderWidth);
    path.lineTo(width - borderWidth, borderWidth);
    path.lineTo(width - borderWidth, height - borderWidth);
    path.lineTo(borderWidth, height - borderWidth);
    path.lineTo(borderWidth, borderWidth);
    canvas.drawPath(path, paint);
    super.onDraw(canvas);

  }
}
