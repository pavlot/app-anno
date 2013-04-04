/**
 * 
 */
package co.usersource.anno.datastore;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Abstract table adapter.
 * 
 * @author Leo
 * 
 */
public abstract class AbstractTableAdapter implements TableAdapter {

  protected SQLiteOpenHelper sqliteOpenHelper;

  public AbstractTableAdapter(SQLiteOpenHelper sqliteOpenHelper) {
    this.sqliteOpenHelper = sqliteOpenHelper;
  }

  /**
   * Return the table name.
   * 
   * @return table name.
   */
  public abstract String getTableName();

  /**
   * Return table initialization sql scripts.
   * 
   * NOTICE: sql execution order is important.
   * 
   * @return init sql scripts.
   */
  public abstract List<String> getInitSqls();

  @Override
  public void onCreate(SQLiteDatabase database) {
    List<String> initSqls = getInitSqls();
    if (initSqls != null) {
      for (String sql : initSqls) {
        database.execSQL(sql);
      }
    }
  }

}
