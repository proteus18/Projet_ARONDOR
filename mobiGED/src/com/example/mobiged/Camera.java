package com.example.mobiged;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends Activity {
 
	// Button
	Button exit;
	Button crop;
	Button valid;
	Button rotate_r;
	Button rotate_l;
 
	// ImageView
	ImageView imgPreview;

	// Current Bitmap
	Bitmap currentBitmap;

	// rotate
	float currentCorner;

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

	private Uri fileUri; // file url to store image/video

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		imgPreview = (ImageView) findViewById(R.id.imgPreview);

		// Buttonffff
		exit = (Button) findViewById(R.id.bt_photo_exit);
		crop = (Button) findViewById(R.id.bt_photo_crop);
		valid = (Button) findViewById(R.id.bt_photo_valid);
		rotate_r = (Button) findViewById(R.id.bt_photo_rotate_r);
		rotate_l = (Button) findViewById(R.id.bt_photo_rotate_l);
		
		//Corner
				currentCorner = 90;
				
				
				//Listener button
				exit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				
				crop.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//crop();
						performCrop(fileUri);
						
					}
				});
				
				valid.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				
				rotate_r.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.e("rotate", "kikou!!!!");
						currentCorner += 90;
						rotate(currentCorner, currentBitmap);
						
					}
				});
				
				rotate_l.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.e("rotate", "kikou!!!!");
						currentCorner -= 90;
						rotate(currentCorner, currentBitmap);
						
					}
				});
				

				takePhoto();

	}//end oncreate
	
	
	private void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				previewCapturedImage();
			} else if (resultCode == Activity.RESULT_CANCELED) {

				// return main page
			} else {
				// msg warning
			}
		}
	};


	private void previewCapturedImage() {
		try {

			imgPreview.setVisibility(View.VISIBLE);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			currentBitmap = BitmapFactory.decodeFile(fileUri.getPath());
			rotate(90, currentBitmap);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}  
	 
	
	

	/*
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		} 

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else { 
			return null;
		}
 
		return mediaFile;
	}

	
	
	
	
	/*
	 * Image tools (En cours)
	 */

	
	
	public void rotate(float r, Bitmap b){

		Display d = getWindowManager().getDefaultDisplay();
		int x = d.getWidth();
		int y = d.getHeight();
		 
		
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, y, x, true);
		
		Matrix matrix = new Matrix();
		matrix.postRotate(r); // anti-clockwise by 90 degrees
		Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
		imgPreview.setImageBitmap(rotatedBitmap);
				
	}


	public void crop(){
		
		 imgPreview.setImageBitmap(currentBitmap.createScaledBitmap(currentBitmap, 150, 150, true));
		 Intent cropIntent = new Intent("com.android.camera.action.CROP");
		 // indicate image type and Uri
		 cropIntent.setDataAndType(fileUri, "image/*");
		 // set crop properties
		 cropIntent.putExtra("crop", "true");
		 // indicate aspect of desired crop
		 cropIntent.putExtra("aspectX", 1);
		 cropIntent.putExtra("aspectY", 1);
		 // indicate output X and Y
		 cropIntent.putExtra("outputX", 256);
		 cropIntent.putExtra("outputY", 256);
		 // retrieve data on return
		 cropIntent.putExtra("return-data", true);
		 // start the activity - we handle returning in onActivityResult
		 startActivityForResult(cropIntent, 1);
	}
	
	private void performCrop(Uri picUri) {
	    try {

	        Intent cropIntent = new Intent("com.android.camera.action.CROP");
	        // indicate image type and Uri
	        cropIntent.setDataAndType(picUri, "image/*");
	        // set crop properties
	        cropIntent.putExtra("crop", "true");
	        // indicate aspect of desired crop
	        cropIntent.putExtra("aspectX", 1);
	        cropIntent.putExtra("aspectY", 1);
	        // indicate output X and Y
	        cropIntent.putExtra("outputX", 128);
	        cropIntent.putExtra("outputY", 128);
	        // retrieve data on return
	        cropIntent.putExtra("return-data", true);
	        // start the activity - we handle returning in onActivityResult
	        startActivityForResult(cropIntent, 2);
	    }
	    // respond to users whose devices do not support the crop action
	    catch (ActivityNotFoundException anfe) {
	        // display an error message
	        String errorMessage = "Whoops - your device doesn't support the crop action!";
	        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
	        toast.show();
	    }
	}

}
