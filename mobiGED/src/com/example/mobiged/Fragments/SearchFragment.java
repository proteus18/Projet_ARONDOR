package com.example.mobiged.Fragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.mobiged.R;
import com.example.mobiged.R.array;
import com.example.mobiged.R.id;
import com.example.mobiged.R.layout;
import com.example.mobiged.adapter.DocumentItemAdapter;
import com.example.mobiged.model.DocumentItem;
import com.example.mobiged.test.TestDataBase;
import com.example.mobiged.util.FragmentCustom;

public class SearchFragment extends Fragment {
  
  
	//Layout
	private RelativeLayout layout;

	// Adapter
	private DocumentItemAdapter adapterC;

	// List of ddocuments
	private ArrayList<DocumentItem> docItems;

	// Filter name
	private String filterName;

	// List view
	private ListView lv;

	// Search EditText
	EditText inputSearch;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (RelativeLayout) inflater.inflate(R.layout.fragment_search, container,
				false);
		
				
		//filter name
		filterName = "tous";

		// ListView
		lv = (ListView) layout.findViewById(R.id.list_view);

		// EditText
		inputSearch = (EditText) layout.findViewById(R.id.inputSearch);

		// instance documents
		docItems = new ArrayList<DocumentItem>();

		// test database
		TestDataBase test = new TestDataBase();
		docItems = test.getDocItems();

		// create adapter
		adapterC = new DocumentItemAdapter(layout.getContext(), docItems);
		lv.setAdapter(adapterC);

		// spinner
		Spinner spinner = (Spinner) layout.findViewById(R.id.filter_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				layout.getContext(), R.array.filter, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				//Choice filter
				filterName = parent.getItemAtPosition(pos).toString();
				inputSearch.setText("");

			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});

		// textChanged
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				SearchFragment.this.adapterC.getFilter(filterName).filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

		// click on item
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//open document
			}
		});

		registerForContextMenu(lv);
					

		return layout;
	}// end onCreate
	

	// context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Document : ");
		menu.add(0, v.getId(), 0, "Ouvrir le document"); 
		menu.add(0, v.getId(), 0, "Suivre le document");
		menu.add(0, v.getId(), 0, "A propos du document");

	}


	

}
