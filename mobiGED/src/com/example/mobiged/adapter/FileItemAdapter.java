package com.example.mobiged.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobiged.R;
import com.example.mobiged.model.FileItem;

public class FileItemAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<FileItem> fileItems;

	/**
	 * @param currentContext
	 * @param fileitems
	 */
	public FileItemAdapter(Context currentContext, ArrayList<FileItem> fileitems) {
		inflater = LayoutInflater.from(currentContext);
		this.fileItems = fileitems;
	}

	private class ViewHolder {
		TextView name;
		ImageView icon;
	}

	@Override
	public int getCount() {
		return fileItems.size();
	}

	@Override
	public Object getItem(int position) {
		return fileItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();
			try {
				convertView = inflater.inflate(R.layout.list_item_file, null);
			} catch (Exception e) {
				Log.e("null", e.getMessage());
			}

			holder.name = (TextView) convertView
					.findViewById(R.id.list_file_name);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.list_file_icon);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(fileItems.get(position).getName());

		if (fileItems.get(position).isFolder()) {
			holder.icon.setImageResource(R.drawable.folder);
		} else {
			holder.icon.setImageResource(getIdFileType(fileItems.get(position)
					.getType()));
		}
		return convertView;
	}

	public int getIdFileType(final String fileType) {

		if (fileType != null) {
			if (fileType.equalsIgnoreCase(".doc")
					|| fileType.equalsIgnoreCase(".docx")) {
				return R.drawable.filetype_word;
			} else if (fileType.equalsIgnoreCase(".zip")
					|| fileType.equalsIgnoreCase(".rar")) {
				return R.drawable.filetype_rar;
			} else if (fileType.equalsIgnoreCase(".ppt")
					|| fileType.equalsIgnoreCase(".pptx")) {
				return R.drawable.filetype_ppt;
			} else if (fileType.equalsIgnoreCase(".pdf")) {
				return R.drawable.filetype_pdf;
			} else if (fileType.equalsIgnoreCase(".png")
					|| fileType.equalsIgnoreCase(".jpg")
					|| fileType.equalsIgnoreCase(".jpeg")) {
				return R.drawable.filetype_img;
			} else if (fileType.equalsIgnoreCase(".xls")) {
				return R.drawable.filetype_excel;
			} 
		}
		return R.drawable.filetype_others;
	}

}
