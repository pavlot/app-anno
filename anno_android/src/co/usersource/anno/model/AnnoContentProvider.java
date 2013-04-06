/**
 * 
 */
package co.usersource.anno.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import co.usersource.anno.datastore.AnnoSQLiteOpenHelper;
import co.usersource.anno.datastore.TableCommentFeedbackAdapter;
import co.usersource.anno.datastore.UnknownUriException;

/**
 * Content Provider for anno app.
 * 
 * @author topcircler
 * 
 */
public class AnnoContentProvider extends ContentProvider {

  public static final String AUTHORITY = "co.usersource.anno.provider";
  public static final String COMMENT_PATH = TableCommentFeedbackAdapter.TABLE_NAME;
  public static final Uri COMMENT_PATH_URI = Uri.parse("content://" + AUTHORITY
      + "/" + COMMENT_PATH);
  private static final int SINGLE_COMMENT_CODE = 1;
  private static final int COMMENT_CODE = 2;

  /* type definitions */
  private static final String TABLE_MIME_TYPE = "vnd";
  private static final String TABLE_MIME_SUBTYPE_FOR_ROW = "android.cursor.item/";
  private static final String TABLE_MIME_SUBTYPE_FOR_ROWS = "android.cursor.dir/";
  private static final String TABLE_NAME_PROVIDER_SPECIFIC = "co.usersource.anno.provider";

  private AnnoSQLiteOpenHelper annoSQLiteOpenHelper;
  private static final UriMatcher URI_MATCHER = new UriMatcher(
      UriMatcher.NO_MATCH);

  static {
    URI_MATCHER.addURI(AUTHORITY, COMMENT_PATH + "/#", SINGLE_COMMENT_CODE);
    URI_MATCHER.addURI(AUTHORITY, COMMENT_PATH, COMMENT_CODE);
  }

  @Override
  public boolean onCreate() {
    annoSQLiteOpenHelper = new AnnoSQLiteOpenHelper(getContext());
    return true;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public String getType(Uri uri) {
    int code = URI_MATCHER.match(uri);
    switch (code) {
    case SINGLE_COMMENT_CODE:
      return TABLE_MIME_TYPE + "." + TABLE_MIME_SUBTYPE_FOR_ROW + "/"
          + TABLE_NAME_PROVIDER_SPECIFIC + "." + COMMENT_PATH;
    default:
      handleUnknownUri(uri, code);
    }
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    int code = URI_MATCHER.match(uri);
    switch (code) {
    case COMMENT_CODE:
      long newId = annoSQLiteOpenHelper.getTableCommentFeedbackAdapter()
          .insert(values);
      if (newId != -1) {
        return ContentUris.withAppendedId(COMMENT_PATH_URI, newId);
      }
      throw new SQLException();
    default:
      handleUnknownUri(uri, code);
    }
    return null;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    int code = URI_MATCHER.match(uri);
    switch (code) {
    case COMMENT_CODE:
      return annoSQLiteOpenHelper.getTableCommentFeedbackAdapter().query(
          projection, selection, selectionArgs, sortOrder);
    default:
      handleUnknownUri(uri, code);
    }
    return null;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    int code = URI_MATCHER.match(uri);
    switch (code) {
    case SINGLE_COMMENT_CODE:
      return annoSQLiteOpenHelper.getTableCommentFeedbackAdapter().update(
          values, selection, selectionArgs);
    default:
      handleUnknownUri(uri, code);
    }
    return 0;
  }

  private String handleUnknownUri(Uri uri, int matchCode)
      throws UnknownUriException {
    throw new UnknownUriException(uri, matchCode, String.format(
        "Unknown uri(%s) code(%s).", uri.toString(), matchCode));
  }

}
