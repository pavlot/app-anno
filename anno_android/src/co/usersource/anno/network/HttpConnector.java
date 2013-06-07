/**
 * 
 */
package co.usersource.anno.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author rsh This class intended to provide HTTP transport for sync routines.
 *         This class perform authentication against anno GAE service.
 */
public class HttpConnector {

	/** Timeouts for httpClient */
	public static final int REGISTRATION_TIMEOUT = 30 * 1000; // ms
	/** Base URL for Anno services */
	public static final String BASE_URL = "https://annoserver.appspot.com";
	/** Auth URL part. */
	public static final String AUTH_URI = BASE_URL + "/_ah/login";

	private DefaultHttpClient httpClient;
	private Context applicationContext;
	private IHttpConnectorAuthHandler httpConnectorAuthHandler;
	private List<NameValuePair> params;
	private IHttpRequestHandler httpRequestHandler;

	/**
	 * @return the httpConnectorAuthHandler
	 */
	public synchronized IHttpConnectorAuthHandler getHttpConnectorAuthHandler() {
		return httpConnectorAuthHandler;
	}

	/**
	 * @param httpConnectorAuthHandler
	 *            the httpConnectorAuthHandler to set
	 */
	public synchronized void setHttpConnectorAuthHandler(
			IHttpConnectorAuthHandler httpConnectorAuthHandler) {
		this.httpConnectorAuthHandler = httpConnectorAuthHandler;
	}

	/**
	 * Check whether current instance of the connector has required cookies for
	 * authentication.
	 * 
	 * @return the isAuthenticated
	 */
	public synchronized boolean isAuthenticated() {
		boolean result = false;
		for (Cookie cookie : getHttpClient().getCookieStore().getCookies()) {
			Log.i(this.getClass().getName(),
					"Found cookie: " + cookie.getName());
			if (cookie.getName().equals("SACSID")
					|| cookie.getName().equals("ACSID")) {
				result = true;
			}
			result = true;
		}
		return result;
	}

	/**
	 * Returns HTTP client used for transport routines. Creates new instance if
	 * required.
	 * 
	 * @return HTTP client.
	 */
	private DefaultHttpClient getHttpClient() {
		if (null == httpClient) {
			httpClient = new DefaultHttpClient();
		}
		return httpClient;
	}

	/**
	 * Sends request for given URI with given parameters.
	 * 
	 * @param URI
	 *            - URI for request.
	 * @param params
	 *            - parameters which must send to the server.
	 * 
	 * @param handler
	 *            - handler to be used against response. This handler is
	 *            responsible to parse returned JSON and perform some routines
	 *            on it.
	 * @return - answer from server in JSON format.
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized public void SendRequest(String URI,
			List<NameValuePair> params, IHttpRequestHandler handler)
			throws ParseException, IOException {
		this.params = params;
		this.httpRequestHandler = handler;
		new PerformRequestTask().execute(URI);
	}

	/**
	 * Performs authentication routines for this connector.
	 * @param applicationContext context where this connector executed.
	 * @param account any account from AccountManager (now only google accounts supported).
	 * */
	public void authenticate(Context applicationContext, Account account) {
		this.applicationContext = applicationContext;
		AccountManager accountManager = AccountManager.get(applicationContext);
		accountManager.getAuthToken(account, "ah", null, false,
				new GetAuthTokenCallback(), null);
	}


	/**
	 * This class intended to be used as separate thread to perform request to server.
	 * */
	private class PerformRequestTask extends
			AsyncTask<String, Integer, JSONObject> {
		protected JSONObject doInBackground(String... uri) {
			JSONObject result = null;
			try {
				final HttpPost postRequest = new HttpPost(BASE_URL + uri[0]);
				HttpEntity entity = null;

				entity = new UrlEncodedFormEntity(params);
				postRequest.addHeader(entity.getContentType());
				postRequest.setEntity(entity);

				final HttpParams HttpClientParams = getHttpClient().getParams();
				HttpConnectionParams.setConnectionTimeout(HttpClientParams,
						REGISTRATION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(HttpClientParams,
						REGISTRATION_TIMEOUT);
				ConnManagerParams.setTimeout(HttpClientParams,
						REGISTRATION_TIMEOUT);

				final HttpResponse response = getHttpClient().execute(
						postRequest);

				final String data = EntityUtils.toString(response.getEntity());
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					try {
						result = new JSONObject(data);
					} catch (JSONException e) {
						Log.v(this.getClass().getName(),
								"Cannot parse json from server!!!");
						e.printStackTrace();
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				getHttpClient().getParams().setBooleanParameter(
						ClientPNames.HANDLE_REDIRECTS, true);
			}
			return result;
		}

		protected void onPostExecute(JSONObject result) {
			if (httpRequestHandler != null) {
				httpRequestHandler.onRequest(result);
			}
		}
	}

	/** This class intended to be used to obtain auth cookies for current instance. */
	private class GetCookieTask extends AsyncTask<String, Integer, Boolean> {
		protected Boolean doInBackground(String... tokens) {
			try {
				// Don't follow redirects
				getHttpClient().getParams().setBooleanParameter(
						ClientPNames.HANDLE_REDIRECTS, false);

				HttpGet http_get = new HttpGet(AUTH_URI + "?continue="  + BASE_URL + "&auth=" + tokens[0]);
				
				HttpResponse response;
				response = getHttpClient().execute(http_get);
				if (response.getStatusLine().getStatusCode() != 302) {
					// Response should be a redirect
					return false;
				}
				if (isAuthenticated()) {
					return true;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				getHttpClient().getParams().setBooleanParameter(
						ClientPNames.HANDLE_REDIRECTS, true);
			}
			return false;
		}

		protected void onPostExecute(Boolean result) {
			if (httpConnectorAuthHandler != null) {
				if (isAuthenticated()) {
					httpConnectorAuthHandler.onAuthSuccess();
				} else {
					httpConnectorAuthHandler.onAuthFail();
				}
			}
		}
	}

	/** This class used to obtain auth data from AccountManager. */
	private class GetAuthTokenCallback implements
			AccountManagerCallback<Bundle> {
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			try {
				bundle = result.getResult();
				Intent intent = (Intent) bundle.get(AccountManager.KEY_INTENT);
				if (null != intent) {
					applicationContext.startActivity(intent);
				} else {
					String auth_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
					new GetCookieTask().execute(auth_token);
				}
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
