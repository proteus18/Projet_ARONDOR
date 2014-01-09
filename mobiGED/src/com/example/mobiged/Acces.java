package com.example.mobiged;

import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.Drive;

public class Acces {

	private static Drive service;
	final private static List<String> SCOPES = Arrays.asList(new String[] {
			"https://www.googleapis.com/auth/plus.login",
			"https://www.googleapis.com/auth/drive" });
	private static GoogleAccountCredential mCredential;
	
	public static Drive getService() {
		return service;
	}//
	public static void setService(Drive service) {
		Acces.service = service;
	}
	public static GoogleAccountCredential getmCredential() {
		return mCredential;
	}
	public static void setmCredential(GoogleAccountCredential mCredential) {
		Acces.mCredential = mCredential;
	}
	public static List<String> getSCOPES() {
		return SCOPES;
	}
}
