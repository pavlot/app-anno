/**
 * 
 */
package co.usersource.anno;

import android.app.Application;
import co.usersource.anno.utils.AppConfig;

/**
 * Global application data.
 * 
 * @author topcircler
 * 
 */
public class AnnoApplication extends Application {

  private AppConfig config = null;

  /**
   * @return the config. application configuration.
   */
  public AppConfig getConfig() {
    if (config == null) {
      config = new AppConfig(getApplicationContext());
    }
    return config;
  }

}
