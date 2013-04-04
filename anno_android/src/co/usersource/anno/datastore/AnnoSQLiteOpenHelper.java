/**
 * 
 */
package co.usersource.anno.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class intends to provide access and creation routines for Anno program
 * database.
 * 
 * @author Leo
 * 
 */
public class AnnoSQLiteOpenHelper extends SQLiteOpenHelper {

  /** Database name. */
  public static final String DATABASE_NAME = "anno.db";
  /** Version for upgrade routines. */
  public static final int DATABASE_VERSION = 1;

  /* table adapters */
  private TableAdapter tableCommentFeedbackAdapter;

  public AnnoSQLiteOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    tableCommentFeedbackAdapter = new TableCommentFeedbackAdapter(this);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    tableCommentFeedbackAdapter.onCreate(database);
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    // initial version doesn't need upgrade.
  }

  /**
   * @return the tableCommentFeedbackAdapter
   */
  public TableAdapter getTableCommentFeedbackAdapter() {
    return tableCommentFeedbackAdapter;
  }

}
