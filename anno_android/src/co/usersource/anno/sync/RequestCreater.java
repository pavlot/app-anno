package co.usersource.anno.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	JSONObject request;
	JSONArray objects;
	
	public RequestCreater() {
		request = new JSONObject();
		objects = new JSONArray();
	}
	
	public void addObject(Cursor data)
	{
		JSONObject object = new JSONObject();
		
		try {

			object.put(JSON_CLIENT_ID, data.getString(data.getColumnIndex("_id")));
			object.put(JSON_COMMENT, data.getString(data.getColumnIndex("comment")));
			object.put(JSON_SCREEN_KEY, data.getString(data.getColumnIndex("screenshot_key")));
			object.put(JSON_X, data.getString(data.getColumnIndex("x")));
			object.put(JSON_Y, data.getString(data.getColumnIndex("y")));
			object.put(JSON_DIRECTION, data.getString(data.getColumnIndex("direction")));
			
			objects.put(object);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}

}
