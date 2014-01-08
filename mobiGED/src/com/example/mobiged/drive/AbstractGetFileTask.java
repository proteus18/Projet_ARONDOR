package com.example.mobiged.drive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

public abstract class AbstractGetFileTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "TokenFileTask";
	protected SplashActivity mActivity;
	private static String GOOGLE_FILE_DATA = "No_data";
	protected String mScope;
	protected String mEmail;
	protected String mTitreFile;
	protected int mRequestCode;

	public static String getGOOGLE_FILE_DATA() {
		//Log.e("GOOGLE FILE DATA",GOOGLE_FILE_DATA);
		return GOOGLE_FILE_DATA;
	}

	public static void setGOOGLE_FILE_DATA(String gOOGLE_FILE_DATA) {
		GOOGLE_FILE_DATA = gOOGLE_FILE_DATA;
	}

	AbstractGetFileTask(SplashActivity activity, String titre, String scope) {
		this.mActivity = activity;
		this.mScope = scope;
		this.mTitreFile = titre;

	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			fetchFileFromProfileServer();

		} catch (IOException ex) {
			onError("Following Error occured, please try again. "
					+ ex.getMessage(), ex);
		} catch (JSONException e) {
			onError("Bad response: " + e.getMessage(), e);
		}
		return null;
	}

	protected void onError(String msg, Exception e) {
		if (e != null) {
			Log.e(TAG, "Exception: ", e);
		}
	}

	/**
	 * Get a authentication token if one is not available. If the error is not
	 * recoverable then it displays the error message on parent activity.
	 */
	protected abstract String fetchToken() throws IOException;

	/**
	 * Contacts the user info server to get the profile of the user and extracts
	 * the first name of the user from the profile. In order to authenticate
	 * with the user info server the method first fetches an access token from
	 * Google Play services.
	 * 
	 * @return
	 * @return
	 * 
	 * @throws IOException
	 *             if communication with user info server failed.
	 * @throws JSONException
	 *             if the response from the server could not be parsed.
	 */
	private void fetchFileFromProfileServer() throws IOException, JSONException {
		String token;
			token = getToken(mActivity, mEmail, mScope);
			if (token != ""){
				URL url = new URL("https://www.googleapis.com/drive/v2/files?access_token="+ token);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				int sc = con.getResponseCode();
				if (sc == 200) {
					InputStream is = con.getInputStream();
					GOOGLE_FILE_DATA = readResponse(is);
					is.close();
					Intent intent = new Intent(mActivity, HomeActivity.class);
					intent.putExtra("email_id", mEmail);
					intent.putExtra("title",mTitreFile );
					mActivity.startActivity(intent);
					mActivity.finish();
					return;
				} else if (sc == 401) {
					GoogleAuthUtil.invalidateToken(mActivity, token);
					onError("Server auth error, please try again.", null);
					return;
				} else {
					onError("Server returned the following error code: " + sc, null);
					return;
				}
			} else {
				Log.e("TAG TOKEN AGFT"," ligne 120");
			}
			
	}

	public String getToken(SplashActivity splAct, String mail, String scope){
		String token="";
		try {
			token = GoogleAuthUtil.getToken(splAct.getApplicationContext(), "laurent.cogotti@gmail.com", scope);
		} catch (UserRecoverableAuthException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GoogleAuthException e) {
			e.printStackTrace();
		}
		return token;

	}
	
	/**
	 * Reads the response from the input stream and returns it as a string.
	 */
	private static String readResponse(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] data = new byte[2048];
		int len = 0;
		while ((len = is.read(data, 0, data.length)) >= 0) {
			bos.write(data, 0, len);
		}
		return new String(bos.toByteArray(), "UTF-8");
	}
}
