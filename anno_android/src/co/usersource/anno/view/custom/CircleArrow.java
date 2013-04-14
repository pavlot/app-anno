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
public class CircleArrow extends View {

  private Paint paint;
  private Path path;

  /**
   * @param context
   * @param attrs
   */
  public CircleArrow(Context context, AttributeSet attrs) {
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
    super.onDraw(canvas);

    int width = this.getWidth();
    int height = this.getHeight();
    int borderWidth = 6;

    paint.setAntiAlias(true);
    // draw circle
    paint.setColor(getResources().getColor(android.R.color.darker_gray));
    float outRadius = width / 2;
    canvas.drawCircle(outRadius, outRadius, outRadius, paint);
    float innerRadius = outRadius - borderWidth;
    paint.setColor(getResources().getColor(R.color.transparent_orange));
    canvas.drawCircle(outRadius, outRadius, innerRadius, paint);
    // draw right edge
    paint.setColor(getResources().getColor(android.R.color.black));
    path.reset();
    path.moveTo(outRadius, outRadius);
    path.lineTo(width, height);
    path.lineTo(width - borderWidth, height);
    path.lineTo(outRadius, outRadius + borderWidth);
    path.lineTo(outRadius, outRadius);
    canvas.drawPath(path, paint);
    // draw left edge
    path.reset();
    path.moveTo(outRadius, outRadius);
    path.lineTo(0, height);
    path.lineTo(borderWidth, height);
    path.lineTo(outRadius, outRadius + borderWidth);
    path.lineTo(outRadius, outRadius);
    canvas.drawPath(path, paint);
    // draw triangle
    path.reset();
    paint.setColor(getResources().getColor(R.color.transparent_orange));
    path.moveTo(outRadius, outRadius + borderWidth);
    path.lineTo(width - borderWidth, height);
    path.lineTo(borderWidth, height);
    path.lineTo(outRadius, outRadius + borderWidth);
    canvas.drawPath(path, paint);
    path.close();
  }
}
