package co.usersource.anno.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import co.usersource.anno.R;

public class AnnoMainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.anno_main, menu);
    return true;
  }

}
