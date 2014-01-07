package com.example.mobiged;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

	ImageView header;
	ImageView footer;

	Button connexion;
	TextView inscription;

	Animation header_up;   
	Animation header_down;
	Animation footer_up;
	Animation footer_down;
	
	ActionBar bar;

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
				Intent i = new Intent(getApplicationContext(), Home.class);
				startActivity(i);

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
