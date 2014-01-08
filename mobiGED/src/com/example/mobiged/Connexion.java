package com.example.mobiged;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Connexion extends Activity implements AnimationListener {

	// PARAMETRES IHM
	ImageView header;
	ImageView footer;

	Button connexion;
	TextView inscription;
	//

	Animation header_up;
	Animation header_down;
	Animation footer_up;
	Animation footer_down;

	ActionBar bar;

	// PARAMETRES FONCTIONS

	static final int MY_ACTIVITYS_AUTH_REQUEST_CODE = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int REQUEST_ACCOUNT_PICKER = 3;
	static final int CAPTURE_IMAGE = 4;

	

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connexion);

		header = (ImageView) findViewById(R.id.img_temp);
		footer = (ImageView) findViewById(R.id.img_footer);

		connexion = (Button) findViewById(R.id.btnLogin);
		inscription = (TextView) findViewById(R.id.link_to_register);

		header_up = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.header_up);
		footer_up = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.footer_up);
		footer_down = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.footer_up);
		header_down = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.header_down);

		header_up.setAnimationListener(this);
		header_down.setAnimationListener(this);
		footer_up.setAnimationListener(this);
		footer_down.setAnimationListener(this);

		header.startAnimation(header_up);
		footer.startAnimation(footer_up);

		connexion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Acces.setmCredential(GoogleAccountCredential.usingOAuth2(
						getApplicationContext(), Acces.getSCOPES()));
				startActivityForResult(Acces.getmCredential().newChooseAccountIntent(),
						REQUEST_ACCOUNT_PICKER);

			}
		});

		inscription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				header.startAnimation(header_down);
				footer.startAnimation(footer_down);

			}
		});

	}

	/**
	 * Handles the callbacks from result returning account picker and permission
	 * requester activities.
	 */
	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		switch (requestCode) {
		case REQUEST_ACCOUNT_PICKER:
			String accountName = data
					.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			if (accountName != null) {
				Acces.getmCredential().setSelectedAccountName(accountName);
				Acces.setService(new Drive.Builder(
						AndroidHttp.newCompatibleTransport(),
						new GsonFactory(), Acces.getmCredential()).setApplicationName(
						"GDive").build());

			}
			getAndUseAuthTokenInAsyncTask();
			break;
		case REQUEST_AUTHORIZATION:
			if (resultCode != Activity.RESULT_OK) {
				startActivityForResult(Acces.getmCredential().newChooseAccountIntent(),
						REQUEST_ACCOUNT_PICKER);
			}
			break;
		case MY_ACTIVITYS_AUTH_REQUEST_CODE:
			if (resultCode == RESULT_OK) {
				getAndUseAuthTokenInAsyncTask();
			}
		}
	}

	// Example of how to use AsyncTask to call blocking code on a background
	// thread.
	void getAndUseAuthTokenInAsyncTask() {
		AsyncTask<Object, Object, Object> task = new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				getAndUseAuthTokenBlocking();
				return params;
			}

			protected void onPostExecute(Object result) {
				Intent i = new Intent(getApplicationContext(), Home.class);
				startActivity(i);
			}

		};
		task.execute((Void) null);
	}

	public Boolean isGoogleServiceAvailable() {
		// CHECK For the version of the Google Play Services
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this.getApplicationContext());
		if (resultCode == ConnectionResult.SUCCESS) {
			System.out.println("success");
			return true;
		} else if (resultCode == ConnectionResult.SERVICE_MISSING
				|| resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
				|| resultCode == ConnectionResult.SERVICE_DISABLED) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 1);
			dialog.show();
			return false;
		} else {
			System.out.println("autre");
		}
		return false;
	}

	void getAndUseAuthTokenBlocking() {
		try {
			System.out.println("lance token");
			final String token = GoogleAuthUtil.getToken(
					getApplicationContext(),
					Acces.getmCredential().getSelectedAccountName(), "oauth2:"
							+ DriveScopes.DRIVE);
			System.out.println("token : " + token);
			return;
		} catch (GooglePlayServicesAvailabilityException playEx) {
			Dialog alert = GooglePlayServicesUtil.getErrorDialog(
					playEx.getConnectionStatusCode(), this,
					Connexion.MY_ACTIVITYS_AUTH_REQUEST_CODE);
		} catch (UserRecoverableAuthException userAuthEx) {
			startActivityForResult(userAuthEx.getIntent(),
					REQUEST_AUTHORIZATION);

		} catch (IOException transientEx) {

			return;
		} catch (GoogleAuthException authEx) {

			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == footer_down) {
			Intent i = new Intent(getApplicationContext(), Inscription.class);
			startActivity(i);
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
