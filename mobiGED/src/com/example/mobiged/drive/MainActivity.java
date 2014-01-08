package com.example.mobiged.drive;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.driveFilesExtract.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveRequest;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

public class MainActivity extends Activity {
    private static final int CHOOSE_ACCOUNT=0;
    private static String accountName;
    private static int REQUEST_TOKEN=0;
    private Button btn_drive;
    private Context ctx = this;
    private Activity a = this;

    
    
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // set up the GUI layout
        setContentView(R.layout.activity_main);
        // set the variables to access the GUI controls
        btn_drive = (Button) findViewById(R.id.button1);
            btn_drive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseAccount();
            }
            });
    }

    public void chooseAccount() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, CHOOSE_ACCOUNT);
    }

    // Fetch the access token asynchronously.
    void getAndUseAuthTokenInAsyncTask(Account account) {
        AsyncTask<Account, String, String> task = new AsyncTask<Account, String, String>() {
            ProgressDialog progressDlg;
            AsyncTask<Account, String, String> me = this;

            @Override
            protected void onPreExecute() {
                progressDlg = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
                progressDlg.setMax(100);
                progressDlg.setTitle("Validating...");
                progressDlg.setMessage("Verifying the login data you entered...\n\nThis action will time out after 10 seconds.");
                progressDlg.setCancelable(false);
                progressDlg.setIndeterminate(false);
                progressDlg.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface d) {
                        progressDlg.dismiss();
                        me.cancel(true);
                    }
                });
                progressDlg.show();
            }

            @Override
            protected String doInBackground(Account... params) {
                return getAccessToken(params[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    // Wait for the extra intent
                } else {
                    accountName = s;
                    getDriveFiles();
                }
                progressDlg.dismiss();
            }
        };
        task.execute(account);
    }

    /**
     * Fetches the token from a particular Google account chosen by the user.  DO NOT RUN THIS DIRECTLY.  It must be run asynchronously inside an AsyncTask.
     * @param activity
     * @param account
     * @return
     */
    private String getAccessToken(Account account) {
        try {
            return GoogleAuthUtil.getToken(ctx, account.name, "oauth2:" + DriveScopes.DRIVE);  // IMPORTANT: DriveScopes must be changed depending on what level of access you want
        } catch (UserRecoverableAuthException e) {
            // Start the Approval Screen intent, if not run from an Activity, add the Intent.FLAG_ACTIVITY_NEW_TASK flag.
            a.startActivityForResult(e.getIntent(), REQUEST_TOKEN);
            e.printStackTrace();
            return null;
        } catch (GoogleAuthException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Drive getDriveService() {
        HttpTransport ht = AndroidHttp.newCompatibleTransport();             // Makes a transport compatible with both Android 2.2- and 2.3+
        GsonFactory jf = new GsonFactory();                            // You need a JSON parser to help you out with the API response
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accountName);
        HttpRequestFactory rf = ht.createRequestFactory(credential);
        Drive.Builder b = new Drive.Builder(ht, jf, null);
        b.setHttpRequestInitializer(new HttpRequestInitializer() {
			
			@Override
			public void initialize(HttpRequest request) throws IOException {
				DriveRequest driveRequest = (DriveRequest) request.getContent();
				driveRequest.setPrettyPrint(true);
				driveRequest.setOauthToken(accountName);

			}
		});
        return b.build();
    }

    /**
     * Obtains a list of all files on the signed-in user's Google Drive account.
     */
    private void getDriveFiles() {
        Drive service = getDriveService();
        Log.d("SiteTrack", "FUNCTION getDriveFiles()");
        Files.List request;
        try {
            request = service.files().list(); // .setQ("mimeType=\"text/plain\"");
            Log.e("LISTE DOC",request.toString());
        } catch (IOException e) {


            e.printStackTrace();
            return;
        }
        do {
            FileList files = null;
            System.out.println("got here");
			Log.d("SiteTrack", request.toString());
        } while (request.getPageToken() != null && request.getPageToken().length() >= 0);
    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == CHOOSE_ACCOUNT && resultCode == RESULT_OK) {
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            GoogleAccountManager gam = new GoogleAccountManager(this);
            getAndUseAuthTokenInAsyncTask(gam.getAccountByName(accountName));
            Log.d("SiteTrack", "CHOOSE_ACCOUNT");
        } else if (requestCode == REQUEST_TOKEN && resultCode == RESULT_OK) {
            accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Log.d("SiteTrack", "REQUEST_TOKEN");
        }
    }

	public static String getAccountName() {
		return accountName;
	}

	public static void setAccountName(String accountName) {
		MainActivity.accountName = accountName;
	}   
}