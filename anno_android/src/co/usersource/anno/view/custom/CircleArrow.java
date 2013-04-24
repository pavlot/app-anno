/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import co.usersource.anno.R;
import co.usersource.anno.utils.ViewUtils;

/**
 * 
 * 
 * @author topcircler
 * 
 */
public class CircleArrow extends View implements View.OnTouchListener {

  private static final String TAG = "CircleArrow";

  // in dip.
  private static final float DEFAULT_CIRCLE_RADIUS = 20;
  private static final float DEFAULT_CIRCLE_LEFT = 100;
  private static final float BORDER_WIDTH = 6; // in px.

  private float circleRadius;
  private int circleBackgroundColor;
  private int circleBorderColor;
  private int arrowBorderColor;
  private int arrowBackgroundColor;
  private float circleLeft;
  private float arrowLeft;
  private float arrowLeftRightSpace;
  private boolean arrowOnTop;

  private Paint paint;
  private Path path;

  private boolean flag = false;

  /**
   * @param context
   * @param attrs
   */
  public CircleArrow(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.CommentArea);
    circleRadius = a.getDimension(R.styleable.CommentArea_circle_radius,
        ViewUtils.dip2px(context, DEFAULT_CIRCLE_RADIUS));
    circleBackgroundColor = a.getColor(
        R.styleable.CommentArea_circle_background_color,
        R.color.circle_background);
    circleBorderColor = a.getColor(R.styleable.CommentArea_circle_border_color,
        R.color.circle_border);
    arrowBorderColor = a.getColor(R.styleable.CommentArea_arrow_border_color,
        R.color.commentbox_border);
    arrowBackgroundColor = a.getColor(
        R.styleable.CommentArea_arrow_background_color,
        R.color.commentbox_background);
    circleLeft = a.getDimension(R.styleable.CommentArea_circle_left,
        ViewUtils.dip2px(context, DEFAULT_CIRCLE_LEFT));
    arrowLeft = a.getDimension(R.styleable.CommentArea_arrow_left,
        DEFAULT_CIRCLE_LEFT);
    arrowLeftRightSpace = a.getDimension(
        R.styleable.CommentArea_arrow_left_right_space, DEFAULT_CIRCLE_RADIUS);
    arrowOnTop = a.getBoolean(R.styleable.CommentArea_arrow_on_top, true);

    paint = new Paint();
    paint.setAntiAlias(true);
    path = new Path();

    this.setOnTouchListener(this);

    a.recycle();
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

    if (arrowOnTop) {
      drawCircleTop(canvas);
      drawRightLineTop(canvas, height);
      drawLeftLineTop(canvas, height);
      drawTriangleTop(canvas, height);
    } else {
      drawCircleBottom(canvas, height);
      drawRightLineBottom(canvas, height);
      drawLeftLineBottom(canvas, height);
      drawTriangleBottom(canvas, height);
    }
  }

  private void drawTriangleBottom(Canvas canvas, int height) {
    paint.setColor(arrowBackgroundColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, height - circleRadius - BORDER_WIDTH);
    path.lineTo(arrowLeft + arrowLeftRightSpace - BORDER_WIDTH, 0);
    path.lineTo(arrowLeft + BORDER_WIDTH, 0);
    path.lineTo(circleLeft + circleRadius, height - circleRadius - BORDER_WIDTH);
    canvas.drawPath(path, paint);
    path.close();
  }

  private void drawLeftLineBottom(Canvas canvas, int height) {
    paint.setColor(arrowBorderColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, height - circleRadius);
    path.lineTo(arrowLeft, 0);
    path.lineTo(arrowLeft + BORDER_WIDTH, 0);
    path.lineTo(circleLeft + circleRadius, height - circleRadius - BORDER_WIDTH);
    path.lineTo(circleLeft + circleRadius, height - circleRadius);
    canvas.drawPath(path, paint);
  }

  private void drawRightLineBottom(Canvas canvas, int height) {
    paint.setColor(arrowBorderColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, height - circleRadius);
    path.lineTo(arrowLeft + arrowLeftRightSpace, 0);
    path.lineTo(arrowLeft + arrowLeftRightSpace - BORDER_WIDTH, 0);
    path.lineTo(circleLeft + circleRadius, height - circleRadius - BORDER_WIDTH);
    path.lineTo(circleLeft + circleRadius, height - circleRadius);
    canvas.drawPath(path, paint);
  }

  private void drawCircleBottom(Canvas canvas, int height) {
    // draw outer border
    paint.setStyle(Style.STROKE);
    paint.setStrokeWidth(BORDER_WIDTH);
    paint.setColor(circleBorderColor);
    canvas.drawCircle(circleLeft + circleRadius, height - circleRadius,
        circleRadius - BORDER_WIDTH / 2, paint);

    // draw inner circle
    paint.setStyle(Style.FILL);
    float innerRadius = circleRadius - BORDER_WIDTH;
    paint.setColor(circleBackgroundColor);
    canvas.drawCircle(circleLeft + circleRadius, height - circleRadius,
        innerRadius, paint);
  }

  private void drawTriangleTop(Canvas canvas, int height) {
    paint.setColor(arrowBackgroundColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, circleRadius + BORDER_WIDTH);
    path.lineTo(arrowLeft + arrowLeftRightSpace - BORDER_WIDTH, height);
    path.lineTo(arrowLeft + BORDER_WIDTH, height);
    path.lineTo(circleLeft + circleRadius, circleRadius + BORDER_WIDTH);
    canvas.drawPath(path, paint);
    path.close();
  }

  private void drawLeftLineTop(Canvas canvas, int height) {
    paint.setColor(arrowBorderColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, circleRadius);
    path.lineTo(arrowLeft, height);
    path.lineTo(arrowLeft + BORDER_WIDTH, height);
    path.lineTo(circleLeft + circleRadius, circleRadius + BORDER_WIDTH);
    path.lineTo(circleLeft + circleRadius, circleRadius);
    canvas.drawPath(path, paint);
  }

  private void drawRightLineTop(Canvas canvas, int height) {
    paint.setColor(arrowBorderColor);
    path.reset();
    path.moveTo(circleLeft + circleRadius, circleRadius);
    path.lineTo(arrowLeft + arrowLeftRightSpace, height);
    path.lineTo(arrowLeft + arrowLeftRightSpace - BORDER_WIDTH, height);
    path.lineTo(circleLeft + circleRadius, circleRadius + BORDER_WIDTH);
    path.lineTo(circleLeft + circleRadius, circleRadius);
    canvas.drawPath(path, paint);
  }

  private void drawCircleTop(Canvas canvas) {
    // draw outer border
    paint.setStyle(Style.STROKE);
    paint.setStrokeWidth(BORDER_WIDTH);
    paint.setColor(circleBorderColor);
    canvas.drawCircle(circleLeft + circleRadius, circleRadius, circleRadius
        - BORDER_WIDTH / 2, paint);

    // draw inner circle
    paint.setStyle(Style.FILL);
    float innerRadius = circleRadius - BORDER_WIDTH;
    paint.setColor(circleBackgroundColor);
    canvas.drawCircle(circleLeft + circleRadius, circleRadius, innerRadius,
        paint);
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    final int x = (int) event.getRawX();
    final int y = (int) event.getRawY();
    Log.e(TAG, String.format("x:%s y:%s", x, y));
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
    case MotionEvent.ACTION_DOWN:
      if (x >= circleLeft && x <= circleLeft + circleRadius * 2) {
        flag = true;
      }
      break;
    case MotionEvent.ACTION_MOVE:
      if (flag) {
        CommentAreaLayout layout = (CommentAreaLayout) this.getParent();
        layout.move(x, y);
      }
      break;
    case MotionEvent.ACTION_UP:
      flag = false;
      break;
    }
    return true;
  }

  /**
   * @param circleRadius
   *          the circleRadius to set
   */
  public void setCircleRadius(float circleRadius) {
    this.circleRadius = circleRadius;
  }

  /**
   * @param circleBackgroundColor
   *          the circleBackgroundColor to set
   */
  public void setCircleBackgroundColor(int circleBackgroundColor) {
    this.circleBackgroundColor = circleBackgroundColor;
  }

  /**
   * @param circleBorderColor
   *          the circleBorderColor to set
   */
  public void setCircleBorderColor(int circleBorderColor) {
    this.circleBorderColor = circleBorderColor;
  }

  /**
   * @param arrowBorderColor
   *          the arrowBorderColor to set
   */
  public void setArrowBorderColor(int arrowBorderColor) {
    this.arrowBorderColor = arrowBorderColor;
  }

  /**
   * @param arrowBackgroundColor
   *          the arrowBackgroundColor to set
   */
  public void setArrowBackgroundColor(int arrowBackgroundColor) {
    this.arrowBackgroundColor = arrowBackgroundColor;
  }

  /**
   * @param circleLeft
   *          the circleLeft to set
   */
  public void setCircleLeft(float circleLeft) {
    this.circleLeft = circleLeft;
  }

  /**
   * @param arrowLeft
   *          the arrowLeft to set
   */
  public void setArrowLeft(float arrowLeft) {
    this.arrowLeft = arrowLeft;
  }

  /**
   * @param arrowLeftRightSpace
   *          the arrowLeftRightSpace to set
   */
  public void setArrowLeftRightSpace(float arrowLeftRightSpace) {
    this.arrowLeftRightSpace = arrowLeftRightSpace;
  }

  /**
   * @return the circleRadius
   */
  public float getCircleRadius() {
    return circleRadius;
  }

  /**
   * @param arrowOnTop
   *          the arrowOnTop to set
   */
  public void setArrowOnTop(boolean arrowOnTop) {
    this.arrowOnTop = arrowOnTop;
  }

}
