package config;

import com.example.mobiged.R;
import com.example.mobiged.R.string;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 */

public class DialogFrag extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (VarStat.getMessageToBox().equals(getString(R.string.connectez_vous))) {
			builder.setMessage(VarStat.getMessageToBox())
					.setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									
								}
							});
			return builder.create();
		} else {
			builder.setMessage(VarStat.getMessageToBox()).setNegativeButton(
					"Problème DiagFrag.java line 33", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			return builder.create();
		}
	}
}