package com.example.mobiged.drive;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mobiged.Connexion;
import com.example.mobiged.R;
import com.google.android.gms.auth.GoogleAuthUtil;


public class SplashActivity extends Activity {
	private Context mContext = SplashActivity.this;
	private AccountManager mAccountManager;
	private static final String SCOPE_DRIVE = "oauth2:https://www.googleapis.com/auth/drive";


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		syncGoogleAccountDrive();
	}

	private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

	
	private AbstractGetFileTask getTaskDrive(SplashActivity activity, String email, String scope) {
		return new GetFileInForeground(activity, email, scope);
	}
	
	public void syncGoogleAccountDrive() {
		if (isNetworkAvailable() == true) {
			String[] accountarrs = getAccountNames();
			if (accountarrs.length > 0) {
				getTaskDrive(SplashActivity.this, Connexion.getmCredential().getSelectedAccountName(), SCOPE_DRIVE).execute();
			} else {
				Toast.makeText(SplashActivity.this, "No Google Account Sync!", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(SplashActivity.this, "No Network Service!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}
}