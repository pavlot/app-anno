package co.usersource.anno.sync;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.usersource.annoplugin.datastore.TableCommentFeedbackAdapter;

import android.database.Cursor;

public class RequestCreater {
	
	public static final String JSON_CLIENT_ID = "client_id";
	public static final String JSON_COMMENT = "comment";
	public static final String JSON_SCREEN_KEY = "screenshot_key";
	public static final String JSON_X = "x";
	public static final String JSON_Y = "y";
	public static final String JSON_DIRECTION = "direction";
	
	public static final String JSON_UPDATED_OBJECTS = "updatedObjects";
	public static final String JSON_TIME_STAMP = "lastUpdateDate";
	public static final String JSON_OBJECT_KEY = "object_key";
	public static final String JSON_KEYS_COUNT = "keys_count";
	
	public static final String JSON_REQUEST_TYPE = "request_type";
	public static final String JSON_REQUEST_TYPE_KEYS = "generateKeys";
	public static final String JSON_REQUEST_TYPE_UPDATE = "updateData";
	
	public static final String JSON_OBJECTS_KEYS = "objectsKeys";
	
	
	JSONObject request;
	JSONObject keysRequest;
	int keysCount;
	JSONArray objects;
	
	public RequestCreater() {
		request = new JSONObject();
		keysRequest = new JSONObject();
		keysCount = 0;
		objects = new JSONArray();
	}
	
	public void addObject(Cursor data)
	{
		JSONObject object = new JSONObject();
		
		try {

			object.put(JSON_CLIENT_ID, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_ID)));
			object.put(JSON_COMMENT, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_COMMENT)));
			object.put(JSON_SCREEN_KEY, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY)));
			object.put(JSON_X, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_POSITION_X)));
			object.put(JSON_Y, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_POSITION_Y)));
			object.put(JSON_DIRECTION, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_DIRECTION)));
			object.put(JSON_TIME_STAMP, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_TIMESTAMP)));
			
			if(data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_OBJECT_KEY)) == null)	{
				object.put(JSON_OBJECT_KEY, JSONObject.NULL);
				++keysCount; 
				
			}else{
				object.put(JSON_OBJECT_KEY, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_OBJECT_KEY)));
			}
			
			objects.put(object);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> addKeys(JSONObject data){
		
		Map<String, String> result = null;
		try {
			result = new HashMap<String, String>(data.getInt(JSON_KEYS_COUNT));
			for(int i = 0, j = 0; j < data.getJSONArray(JSON_OBJECTS_KEYS).length(); ++i)
			{
				if(objects.getJSONObject(i).getString(JSON_OBJECT_KEY).equals("null"))
				{
					objects.getJSONObject(i).put(JSON_OBJECT_KEY, data.getJSONArray(JSON_OBJECTS_KEYS).getString(j));
					result.put(objects.getJSONObject(i).getString(JSON_CLIENT_ID), data.getJSONArray(JSON_OBJECTS_KEYS).getString(j));
					++j;
					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void addUpdateDate(String date)
	{
		try {
			if(null == date){
				request.put(JSON_TIME_STAMP, "2000-01-01 00:00:00:00");
			}
			else{
				request.put(JSON_TIME_STAMP, date);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject getRequest()
	{
		try {
		
			request.put(JSON_UPDATED_OBJECTS, objects);
			request.put(JSON_REQUEST_TYPE, JSON_REQUEST_TYPE_UPDATE);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}
	
	/**
	 * This method returns request for generate keys 
	 * @return request for generate keys
	 */
	public JSONObject getKeysRequest()
	{
		try {
		
			keysRequest.put(JSON_KEYS_COUNT, keysCount);
			keysRequest.put(JSON_REQUEST_TYPE, JSON_REQUEST_TYPE_KEYS);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keysRequest;
	}

}
