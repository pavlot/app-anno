package co.usersource.anno.sync;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.usersource.anno.network.HttpConnector;
import co.usersource.anno.network.IHttpConnectorAuthHandler;
import co.usersource.anno.network.IHttpRequestHandler;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/**
 * This class implements synchronization with server.
 * 
 * @author Sergey Gadzhilov
 * 
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
	public static final String JSON_REQUEST_PARAM_NAME = "jsonData";
	
	private static final String TAG = "AnnoSyncAdapter";
	private ContentValues m_valuesForUpdate;
	private HttpConnector httpConnector;

       
    /**
     * {@inheritDoc}
     */
    public SyncAdapter(Context context, boolean autoInitialize) 
    {
        super(context, autoInitialize);
        m_valuesForUpdate = new ContentValues();
        
    }

	/**
	 * @return the httpConnector
	 */
	public HttpConnector getHttpConnector() {
		if(httpConnector==null)
		{
			httpConnector = new HttpConnector();
		}
		return httpConnector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		if (getHttpConnector().isAuthenticated()) {
			Log.d(TAG, "httpConnector.isAuthenticated()==true. Perform sync.");
			performSyncRoutines();
		} else {
			Log.d(TAG, "httpConnector.isAuthenticated()==false. Perform auth.");
			getHttpConnector()
					.setHttpConnectorAuthHandler(new IHttpConnectorAuthHandler() {

						public void onAuthSuccess() {
							performSyncRoutines();
						}

						public void onAuthFail() {
							Toast.makeText(getContext(),
									"Auth to sync service failed",
									Toast.LENGTH_LONG).show();
						}
					});
			getHttpConnector().authenticate(getContext(), account);
		}
	}
	
	

	private void performSyncRoutines() {
		Log.v(TAG, "Start synchronization (performSyncRoutines)");
		JSONObject request = new JSONObject();
		try {
			
			request = getLocalData();
			final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(
					SyncAdapter.JSON_REQUEST_PARAM_NAME, request.toString()));
			getHttpConnector().SendRequest("/sync", params, new IHttpRequestHandler() {
				
				public void onRequest(JSONObject response) {
					updateLocalDatabase(response);
				}
			});
			
		} catch (IOException e) {
			Log.v(TAG, "I/O excecption!!!");
			e.printStackTrace();
		}
	}
    
    /**
     * This function reads information from local database.
     */
    private JSONObject getLocalData()
    {
    	return new JSONObject();
    }
    
    
	/**
	 * Generate json object from cursor
	 * 
	 * @param data
	 *            - cursor with data
	 * @param type
	 *            - type of object witch should be generated
	 * @param updatedObjects
	 *            -
	 * @return - json data for object
	 */
	public JSONArray createJSONData(Cursor data, String type,
			JSONArray updatedObjects) {
		return new JSONArray();
	}

	/**
	 * Update local database with data from server
	 * 
	 * @param data
	 *            - json object with data from server
	 */
	private void updateLocalDatabase(JSONObject data) {
		
	}

	/**
	 * Add field to set for update local database
	 * 
	 * @param fieldName
	 *            - name of field in db
	 * @param value
	 *            - JSON object with values
	 * @param valueName
	 *            - value name in json object. If null then fieldName will be
	 *            use as name in json object
	 */
	public void addFieldToUpdate(String fieldName, JSONObject value, String valueName) 
	{
		String data;
		try {
			if (valueName != null) {
				data = value.getString(valueName);
			} else {
				data = value.getString(fieldName);
			}
			m_valuesForUpdate.put(fieldName, data);
		} catch (JSONException e) {
			Log.v(TAG, "Cannot add value for field " + fieldName);
			e.printStackTrace();
		}

	}
}
