package com.example.mobiged.drive;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobiged.R;

public class HomeActivity extends Activity {
	private TextView textViewName, textViewEmail;
	private static String textName, textEmail;
	private ListView viewListTitres;
	private ArrayList<JSONObject> ListeDoc;
	private String[] listeTitres;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_drive);
		
		System.out.println("Mail : " + textEmail);
		textViewName = (TextView) findViewById(R.id.textViewNameValue);
		textViewEmail = (TextView) findViewById(R.id.textViewEmailValue);
		viewListTitres = (ListView)findViewById(R.id.listView1);
		
		/**
		 * get user email using intent
		 */
		Intent intent = getIntent();
		textEmail = intent.getStringExtra("email_id");
		textViewEmail.setText(textEmail);

		/**
		 * get user data from google account
		 */
		try {
			JSONObject fileData = new JSONObject(AbstractGetFileTask.getGOOGLE_FILE_DATA());
			JSONArray jsonArr = fileData.getJSONArray("items");
			// récupération de la métadonnée "Titre"
			ListeDoc = new ArrayList<JSONObject>() {
				private static final long serialVersionUID = 1L;
			};
			for (int i = 0; i < jsonArr.length(); i++) {
				JSONObject childJSONObject = jsonArr.getJSONObject(i);
				if (childJSONObject != null) {
					ListeDoc.add(childJSONObject);
				}
			}
			if (ListeDoc != null) {
				listeTitres = new String[ListeDoc.size()];
				for (int i = 0; i < ListeDoc.size(); i++) {
					try {
						listeTitres[i] = ListeDoc.get(i).getString("title");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (listeTitres != null) {
					viewListTitres.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeTitres));
				}
				textName = "OK";
			} else {
				textName = "Pas de documents dans le drive";
			}

			textViewName.setText(textName);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String getTextName() {
		return textName;
	}



	public static void setTextName(String textName) {
		HomeActivity.textName = textName;
	}



	public static String getTextEmail() {
		return textEmail;
	}



	public static void setTextEmail(String textEmail) {
		HomeActivity.textEmail = textEmail;
	}
	
}