package com.example.mobiged;

import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadPicture extends AsyncTask<Void, Integer, Boolean> {

	private Context context;
	private ProgressDialog mDialog;
    private String mErrorMsg;
    private File body;
    private Long fileLength; 


	public DownloadPicture(Context context) {
		this.context = context;

		mDialog = new ProgressDialog(context);
        mDialog.setMax(100);
        mDialog.setMessage("Uploading");
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setProgress(0);
        mDialog.show();
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			// File's binary content
			java.io.File fileContent = new java.io.File(Camera
					.getFileUri().getPath());
			FileContent mediaContent = new FileContent("image/jpeg",
					fileContent);

			
			// File's metadata.
			body = new File();
			body.setTitle(fileContent.getName());
			body.setMimeType("image/jpeg");
			

			System.out.println("insertion fichier");
			File file = Acces.getService().files().insert(body, mediaContent)
					.execute();
			fileLength=(long) file.size();
			System.out.println(file.size());
		} catch (UserRecoverableAuthIOException e) {
			String accountName = Acces.getmCredential()
					.getSelectedAccountName();
			if (accountName != null) {
				Acces.getmCredential().setSelectedAccountName(accountName);
				Acces.setService(new Drive.Builder(AndroidHttp
						.newCompatibleTransport(), new GsonFactory(), Acces
						.getmCredential()).setApplicationName("GDive").build());
			}
//			Connexion.getAndUseAuthTokenInAsyncTask();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		int percent = (int) (100.0 * (double) progress[0] / fileLength + 0.5);
        mDialog.setProgress(percent);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mDialog.dismiss();
		if (result) {
			Toast.makeText(context, "Photo uploaded!",
					Toast.LENGTH_LONG).show();
		} else {
			showToast(mErrorMsg);
		}
	}

	private void showToast(String msg) {
		Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		error.show();
	}

}
