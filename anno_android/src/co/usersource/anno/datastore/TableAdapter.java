package co.usersource.anno.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classes that implement this interface provide methods which allow simplify
 * table management routines.
 * 
 * @author topcircler
 * 
 */
public interface TableAdapter {

  void onCreate(SQLiteDatabase database);

  long insert(ContentValues values);

  int delete(String arg1, String[] args2);

  int update(ContentValues values, String selection, String[] selectionArgs);

  Cursor query(String[] projection, String selection, String[] selectionArgs,
      String sortOrder);

}
