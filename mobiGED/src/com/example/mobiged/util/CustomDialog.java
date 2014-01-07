package com.example.mobiged.util;

import com.example.mobiged.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Activity c;

	private boolean question;

	private Dialog d;
	private Button ok;

	private Button yes;
	private Button no;
	private int result; // 1 - yes 2 - non

	private String title;
	private String text;

	private TextView title_view;
	private TextView text_view;

	public CustomDialog(Activity a) {
		super(a);
		this.c = a;
		this.title = "Titre";
		this.text = "Message";
		this.question = false;
	}

	public CustomDialog(Activity a, boolean question) {
		super(a);
		this.c = a;
		this.title = "Titre";
		this.text = "Message";
		this.question = question;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getResult() {
		return result;
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_custom_information);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		this.ok = (Button) findViewById(R.id.custom_dialog_bt_ok);
		this.yes = (Button) findViewById(R.id.custom_dialog_bt_yes);
		this.no = (Button) findViewById(R.id.custom_dialog_bt_no);

		this.result = BUTTON_NEUTRAL;

		if (question) {
			this.yes.setVisibility(View.VISIBLE);
			this.no.setVisibility(View.VISIBLE);
			this.ok.setVisibility(View.INVISIBLE);
		} else {
			this.yes.setVisibility(View.INVISIBLE);
			this.no.setVisibility(View.INVISIBLE);
			this.ok.setVisibility(View.VISIBLE);
		}

		this.title_view = (TextView) findViewById(R.id.custom_dialog_title);
		this.text_view = (TextView) findViewById(R.id.custom_dialog_text);

		this.title_view.setText(this.title);
		this.text_view.setText(this.text);

		ok.setOnClickListener(this);
		yes.setOnClickListener(this);
		no.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_dialog_bt_ok:
			result = BUTTON_NEUTRAL;
			dismiss();
			break;
		case R.id.custom_dialog_bt_yes:
			result = BUTTON_POSITIVE;
			dismiss();
			break;
		case R.id.custom_dialog_bt_no:
			result = BUTTON_NEGATIVE;
			dismiss();
			break;
		default:
			break;
		}

	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}
}
