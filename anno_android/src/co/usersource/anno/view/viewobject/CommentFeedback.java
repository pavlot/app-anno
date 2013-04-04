package co.usersource.anno.view.viewobject;

/**
 * Comment Feedback.
 * 
 * Users can add some comments on certain part of the screen shot.
 * 
 * @author Leo
 * 
 */
public class CommentFeedback extends FeedbackItem {

  /**
   * Comment content.
   */
  private String comment;

  /**
   * (x,y) position where comment is added on the screen shot.
   * 
   * x is calculated from left, y is calculated from top.
   * 
   * TODO: further consider on different resolution screen.
   */
  private double x;
  private double y;

  public CommentFeedback(String comment, double x, double y) {
    this.comment = comment;
    this.x = x;
    this.y = y;
  }

  /**
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @return the x
   */
  public double getX() {
    return x;
  }

  /**
   * @return the y
   */
  public double getY() {
    return y;
  }

}
