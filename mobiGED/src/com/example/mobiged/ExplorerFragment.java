package com.example.mobiged;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobiged.adapter.FileItemAdapter;
import com.example.mobiged.model.FileItem;

public class ExplorerFragment extends Fragment implements AnimationListener{

	private static final String root = "/sdcard/";
	private static final int TEXTE_LENGHT = 30;
	private static final int TIME_OPEN_ANIMATION = 500;
	
	
	//Animation
	ImageView footer;
	Animation footer_up;
	Animation footer_down;
	
	private File currentFile;
	private FileItemAdapter adapter;

	

	// Layout
	private RelativeLayout layout;
	
	private ListView lv;
	
	private TextView viewPath;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (RelativeLayout) inflater.inflate(R.layout.fragment_explorer,
				container, false);

		currentFile = new File(root);
		
		lv = (ListView) layout.findViewById(R.id.list_file);
		
		viewPath = (TextView) layout.findViewById(R.id.current_file);
		
		
		//Animation
		footer = (ImageView) layout.findViewById(R.id.footer_explorer);		
		footer_up = AnimationUtils.loadAnimation(layout.getContext(),
				R.anim.footer_up);
		footer_down = AnimationUtils.loadAnimation(layout.getContext(),
				R.anim.footer_down);
		footer_up.setAnimationListener(this);
		footer_down.setAnimationListener(this);
		
		//File explorer
		fill(currentFile);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				FileItem file = (FileItem) adapter.getItem(position);
				if (file.isFolder() || file.getName().equalsIgnoreCase("..")){
					currentFile = new File(file.getPath());
					fill(currentFile);
				}else
				{
					Toast.makeText(layout.getContext(), file.getType(), Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
								
						footer.setVisibility(View.VISIBLE);
						footer.startAnimation(footer_up);
						

				return false;
			}

		});
		
		footer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				footer.startAnimation(footer_down);
				footer.setVisibility(View.INVISIBLE);
				
			}
		});
		
		
		return layout;
	}// end onCreate

	private void fill(File f) {
		File[] dirs = f.listFiles();
		viewPath.setText(f.getAbsolutePath());
		
		ArrayList<FileItem> dir = new ArrayList<FileItem>();
		ArrayList<FileItem> fls = new ArrayList<FileItem>();
		try{
			for (File ff : dirs){
				if(ff.isDirectory()){
					dir.add(new FileItem(ff.getName(), null, true, ff.getAbsolutePath()));
				}
				else
				{
					fls.add(new FileItem(ff.getName(), getFileExt(ff.getName()), false, ff.getAbsolutePath()));
				}
			}
		}
		catch (Exception e){
			Log.e("File chooser",e.getMessage());
		}
		

		Collections.sort(dir);
		Collections.sort(fls);		
		dir.addAll(fls);
		
		if (!f.getName().equalsIgnoreCase("sdcard"))
			dir.add(0, new FileItem("..", null,false, f.getParent()));
		
		
		adapter = new FileItemAdapter(layout.getContext(), dir);
				
		lv.setAdapter(adapter);		
	}
	
	
	
	/*
	 * Tools files
	 */
	
	 public static String getFileExt(String filename) {
	        int pos = filename.lastIndexOf(".");
	        if (pos > -1) {
	            return filename.substring(pos);
	        } else {
	            return filename;
	        }
	    }
	 
	 public static String cutTitle(String title){
		 String tempTitle = "";
		 
		 for(int i = 0; i < TEXTE_LENGHT; i++){
			 tempTitle += title.charAt(i);
		 }		 
		 return tempTitle;		 		 
	 }

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
