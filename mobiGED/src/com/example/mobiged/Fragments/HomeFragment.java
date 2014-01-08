package com.example.mobiged.Fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mobiged.Camera;
import com.example.mobiged.ExplorerFragment;
import com.example.mobiged.R;
import com.example.mobiged.drive.SplashActivity;
import com.example.mobiged.drive.HomeActivity;
import com.example.mobiged.util.CustomDialog;

public class HomeFragment extends Fragment implements AnimationListener {
	
	
	
	//Footer
	ImageView footer;
	Animation footer_up;	
	
	//Button
	Button bt_search;
	Button bt_scan;
	Button bt_contact;
	Button bt_news;
	Button bt_file;
	Button bt_setting;
	
	
	//Dialog
	Dialog diag;
	Builder mainDiag;
	CustomDialog diagCustom;
	
	
	//Layout
	LinearLayout layout;
	
	//Fragment
	FragmentManager fragmentManager;
		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.fragment_home, container,
				false);

		
		//getFragment
		fragmentManager = getFragmentManager();
		
		//Dialog
		diag = new Dialog(layout.getContext());
		mainDiag = new AlertDialog.Builder(layout.getContext());
		
		//Animation
		footer = (ImageView) layout.findViewById(R.id.img_footer);		
		footer_up = AnimationUtils.loadAnimation(layout.getContext(),
				R.anim.footer_up);		
		footer_up.setAnimationListener(this);		
		footer.startAnimation(footer_up);
		
		//Button
		bt_search = (Button) layout.findViewById(R.id.btn_search);
		bt_scan = (Button) layout.findViewById(R.id.btn_scan);
		bt_contact = (Button) layout .findViewById(R.id.btn_contact);
		bt_news = (Button) layout.findViewById(R.id.btn_actu);
		bt_file = (Button) layout.findViewById(R.id.btn_file);
		bt_setting = (Button) layout.findViewById(R.id.btn_setting);
		
			
		
		//Listener search function
		bt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, new SearchFragment()).commit();
			}
		});
		
		
		bt_search.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				/*
				mainDiag.setTitle("Fonction recherche");
				mainDiag.setMessage("bla bla sur la fonction recherche");
				mainDiag.setCancelable(true);
				mainDiag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
					}
				});
					

				mainDiag.show();
				diagCustom.setTitle("Rechercher");
				diagCustom.setText("Cette fonction vous permet de recherche tous les document sur votre GED mobile.");
				diagCustom.show();*/
				
				return false;
			}
		});
		
		//put if restriction cam
		//listener scan fonction
		bt_scan.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				final Intent i = new Intent(layout.getContext(), Camera.class);
				startActivity(i);
				
			}
			
		});
		 
		
		//listener contact fonction
		bt_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Intent i = new Intent(layout.getContext(), SplashActivity.class);
				startActivity(i);
				
			}
		});
		
		
		//listener news fonction
		bt_news.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//listener file fonction
		bt_file.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragmentManager.beginTransaction()
				.replace(R.id.frame_container, new ExplorerFragment()).commit();
				
			}
		});
		
		
		//Listener dashboard fonction
		bt_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		return layout;
	}// end oncreate

	

	//Override of animations
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
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
