/**
 * 
 */
package co.usersource.anno.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;
import co.usersource.anno.R;

/**
 * @author topcircler
 * 
 */
public class BorderedEditText extends EditText {

  public static final String TOP = "top";
  public static final String BOTTOM = "bottom";
  public static final String RIGHT = "right";
  public static final String LEFT = "left";
  public static final String NONE = "none";

  private static final int borderStrokeWidth = 6;
  private static final int borderColor = Color.BLACK;

  private String emptyBorderDirection;
  private float emptyBorderStart;
  private float emptyBorderEnd;

  private Paint paint;

  /**
   * @param context
   * @param attrs
   */
  public BorderedEditText(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditText);
    emptyBorderDirection = a
        .getString(R.styleable.EditText_border_empty_direction);
    if (!NONE.equals(emptyBorderDirection)) {
      emptyBorderStart = a.getDimension(
          R.styleable.EditText_border_empty_start, 0);
      emptyBorderEnd = a.getDimension(R.styleable.EditText_border_empty_end, 0);
    }
    a.recycle();

    paint = new Paint();
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.widget.TextView#onDraw(android.graphics.Canvas)
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // int width = this.getWidth();
    // int height = this.getHeight();
    //
    // paint.setStyle(Paint.Style.STROKE);
    // paint.setColor(borderColor);
    // paint.setStrokeWidth(borderStrokeWidth);
    // int y = height - borderStrokeWidth / 2;
    // if (BOTTOM.equals(emptyBorderDirection)) {
    // canvas.drawLine(0, y, emptyBorderStart, y, paint);
    // canvas.drawLine(emptyBorderEnd, y, width, y, paint);
    // } else {
    // canvas.drawLine(0, y, width, y, paint);
    // }
    // int x = width - borderStrokeWidth / 2;
    // if (RIGHT.equals(emptyBorderDirection)) {
    // canvas.drawLine(x, 0, x, emptyBorderStart, paint);
    // canvas.drawLine(x, emptyBorderEnd, x, height, paint);
    // } else {
    // canvas.drawLine(x, 0, x, height, paint);
    // }
    // x = borderStrokeWidth / 2;
    // if (LEFT.equals(emptyBorderDirection)) {
    // canvas.drawLine(x, 0, x, emptyBorderStart, paint);
    // canvas.drawLine(x, emptyBorderEnd, x, height, paint);
    // } else {
    // canvas.drawLine(x, 0, x, height, paint);
    // }
    // y = borderStrokeWidth / 2;
    // if (TOP.equals(emptyBorderDirection)) {
    // canvas.drawLine(0, y, emptyBorderStart, y, paint);
    // canvas.drawLine(emptyBorderEnd, y, width, y, paint);
    // } else {
    // canvas.drawLine(0, y, width, y, paint);
    // }
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.widget.TextView#onMeasure(int, int)
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    
  }

  /**
   * @param emptyBorderDirection
   *          the emptyBorderDirection to set
   */
  public void setEmptyBorderDirection(String emptyBorderDirection) {
    this.emptyBorderDirection = emptyBorderDirection;
  }

  /**
   * @param emptyBorderStart
   *          the emptyBorderStart to set
   */
  public void setEmptyBorderStart(int emptyBorderStart) {
    this.emptyBorderStart = emptyBorderStart;
  }

  /**
   * @param emptyBorderEnd
   *          the emptyBorderEnd to set
   */
  public void setEmptyBorderEnd(int emptyBorderEnd) {
    this.emptyBorderEnd = emptyBorderEnd;
  }

}
