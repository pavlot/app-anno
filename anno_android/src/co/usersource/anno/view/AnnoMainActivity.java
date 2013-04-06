package co.usersource.anno.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import co.usersource.anno.R;
import co.usersource.anno.datastore.TableCommentFeedbackAdapter;
import co.usersource.anno.model.AnnoContentProvider;

public class AnnoMainActivity extends FragmentActivity implements
    LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener {
  /*
   * consider using section list.
   * 
   * TODO: android-section-list: http://code.google.com/p/android-section-list/
   */

  private static final String TAG = AnnoMainActivity.class.getSimpleName();
  private static final String[] PROJECTION = {
      TableCommentFeedbackAdapter.COL_ID,
      TableCommentFeedbackAdapter.COL_COMMENT };
  private static final int URL_LOADER_COMMENTS = 0;

  private SimpleCursorAdapter adapter;
  private ListView feedbackListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    setComponent();
    loadComments();
  }

  private void setComponent() {
    feedbackListView = (ListView) this.findViewById(R.id.feedbackList);
    String[] bindFrom = new String[] { TableCommentFeedbackAdapter.COL_COMMENT };
    int[] bindTo = new int[] { R.id.commentLabel };
    adapter = new SimpleCursorAdapter(getApplicationContext(),
        R.layout.comment_row, null, bindFrom, bindTo, 0);
    feedbackListView.setAdapter(adapter);
    feedbackListView.setOnItemClickListener(this);
  }

  private void loadComments() {
    getSupportLoaderManager().initLoader(URL_LOADER_COMMENTS, null, this);
    Log.d(TAG, "start query comments.");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.anno_main, menu);
    return true;
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    loadComments();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (adapter != null) {
      if (adapter.getCursor() != null) {
        adapter.getCursor().close();
      }
    }
  }

  @Override
  public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
    switch (loaderId) {
    case URL_LOADER_COMMENTS:
      return new CursorLoader(this, AnnoContentProvider.COMMENT_PATH_URI,
          PROJECTION, null, null, null);
    default:
      // An invalid id was passed in.
      return null;
    }
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    Log.d(TAG, "query finishes.");
    if (adapter != null && cursor != null) {
      adapter.swapCursor(cursor);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    Log.d(TAG, "query reset.");
    if (adapter != null) {
      adapter.swapCursor(null);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View v, int position,
      long id) {
    Log.d(TAG, String.format("item %s(id:%d) was clicked.", position, id));
    // TODO:
  }

}
