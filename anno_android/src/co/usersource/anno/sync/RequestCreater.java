package co.usersource.anno.sync;

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

			object.put(JSON_CLIENT_ID, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_ID)));
			object.put(JSON_COMMENT, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_COMMENT)));
			object.put(JSON_SCREEN_KEY, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_SCREENSHOT_KEY)));
			object.put(JSON_X, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_POSITION_X)));
			object.put(JSON_Y, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_POSITION_Y)));
			object.put(JSON_DIRECTION, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_DIRECTION)));
			object.put(JSON_TIME_STAMP, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_TIMESTAMP)));
			
			if(data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_OBJECT_KEY)) == null)	{
				object.put(JSON_OBJECT_KEY, JSONObject.NULL);
				
			}else{
				object.put(JSON_OBJECT_KEY, data.getString(data.getColumnIndex(TableCommentFeedbackAdapter.COL_OBJECT_KEY)));
			}
			
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
