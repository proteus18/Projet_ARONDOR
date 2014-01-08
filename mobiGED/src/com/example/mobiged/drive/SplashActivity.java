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


/**
 *  Avtivité de transit (affichage d'un message d'attente accompagné d'une progressBar)
 *  permettant de lancer la synchronisation avec un compte google Drive et de lancer 
 *  l'activité suivante.
 *
 */
public class SplashActivity extends Activity {
	private Context mContext = SplashActivity.this;
	private AccountManager mAccountManager;
	private static final String SCOPE_DRIVE = "oauth2:https://www.googleapis.com/auth/drive";

	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		syncGoogleAccountDrive();
	}

	/**
	 * Méthode qui permet, si et seulement si l'accès à internet est possible, de
	 * lancer l'appel à la synchronisation avec le compte google Drive
	 * 
	 */
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
	
	/**
	 * Méthode permettant d'exécuter la tache de connexion au drive afin
	 * de récupérer des fichiers (métadonnées, etc. ) du drive
	 * 
	 * @param activity (activité courante)
	 * @param email (mail du compte google choisi pour l'application) 
	 * @param scope (scope d'accès au Drive du compte choisi)
	 * @return AbstractGetFileTask 
	 */
	private AbstractGetFileTask getTaskDrive(SplashActivity activity, String email, String scope) {
		return new GetFileInForeground(activity, email, scope);
	}
	
	/**
	 * Méthode récupérant les comptes google déjà connu de l'appareil
	 * Retourne un tableau de string correspondant aux mails Gmail connus sur 
	 * l'appareil.
	 * 
	 * @return names
	 */
	private String[] getAccountNames() {
		mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = accounts[i].name;
		}
		return names;
	}

		
	/**
	 * Méthode vérifiant l'accès à la connexion de l'appareil
	 * Renvoie TRUE si l'accès à internet est disponible
	 * Renvoie FALSE sinon
	 * 
	 * @return Boolean 
	 */
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