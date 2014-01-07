package com.example.mobiged.adapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.mobiged.R;
import com.example.mobiged.R.drawable;
import com.example.mobiged.R.id;
import com.example.mobiged.R.layout;
import com.example.mobiged.model.DocumentItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class DocumentItemAdapter extends BaseAdapter implements Filterable {

	private LayoutInflater inflater;
	private ArrayList<DocumentItem> docItems;
	private ArrayList<DocumentItem> docItems_temp;

	private class ViewHolder {
		TextView title;
		TextView author;
		TextView date;
		ImageView icon;
	}

	public DocumentItemAdapter(Context context, ArrayList<DocumentItem> docItems) {
		inflater = LayoutInflater.from(context);
		this.docItems = docItems;
		this.docItems_temp = docItems;
	}

	@Override
	public int getCount() {
		return docItems_temp.size();
	}

	@Override
	public Object getItem(int position) {
		return docItems_temp.get(position);
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
			convertView = inflater.inflate(R.layout.list_item_search, null);

			holder.icon = (ImageView) convertView
					.findViewById(R.id.image_type_document);
			holder.date = (TextView) convertView
					.findViewById(R.id.date_document);
			holder.title = (TextView) convertView
					.findViewById(R.id.title_document);
			holder.author = (TextView) convertView
					.findViewById(R.id.author_document);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.author.setText(docItems_temp.get(position).getAuthor());
		holder.date.setText(DateFormat.getDateTimeInstance().format(
				docItems_temp.get(position).getDate()));

		holder.icon.setImageResource(getIdFileType(docItems_temp.get(position).getType()));

		holder.title.setText(docItems_temp.get(position).getTitre());

		return convertView;
	}

	public Filter getFilter(final String filterName) {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {

				charSequence = charSequence.toString().toLowerCase();
				FilterResults results = new FilterResults();

				if (charSequence == null || charSequence.length() == 0) {
					results.values = docItems;
					results.count = docItems.size();
				} else {
					ArrayList<DocumentItem> filterResultsData = new ArrayList<DocumentItem>();

					if (filterName.equals("Tous")) {
						// tous
						for (DocumentItem data : docItems) {
							if (data.getTitre().toLowerCase()
									.contains(charSequence)
									|| data.getAuthor().toLowerCase()
											.contains(charSequence)
									|| data.getType().toLowerCase()
											.contains(charSequence)) {
								filterResultsData.add(data);
							}
						}

					} else if (filterName.equals("Titre")) {
						// titre
						for (DocumentItem data : docItems) {
							if (data.getTitre().toLowerCase()
									.contains(charSequence)) {
								filterResultsData.add(data);
							}
						}
					} else if (filterName.equals("Auteur")) {
						// auteur
						for (DocumentItem data : docItems) {
							if (data.getAuthor().toLowerCase()
									.contains(charSequence)) {
								filterResultsData.add(data);
							}
						}
					} else if (filterName.equals("Type")) {
						// type
						for (DocumentItem data : docItems) {
							if (data.getType().toLowerCase()
									.contains(charSequence)) {
								filterResultsData.add(data);
							}
						}
					} else if (filterName.equals("Date")) {
						// date
						for (DocumentItem data : docItems) {
							if (data.getDate().toString().toLowerCase()
									.contains(charSequence)) {
								filterResultsData.add(data);
							}
						}
					}

					results.values = filterResultsData;
					results.count = filterResultsData.size();
				}

				return results;
			}

			@Override
			protected void publishResults(CharSequence charSequence,
					FilterResults filterResults) {
				docItems_temp = (ArrayList<DocumentItem>) filterResults.values;
				notifyDataSetChanged();
			}
		};
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIdFileType(final String fileType) {

		if (fileType.equals("word")) {
			return R.drawable.filetype_word;
		} else if (fileType.equals("rar")) {
			return R.drawable.filetype_rar;
		} else if (fileType.equals("ppt")) {
			return R.drawable.filetype_ppt;
		} else if (fileType.equals("pdf")) {
			return R.drawable.filetype_pdf;
		} else if (fileType.equals("img")) {
			return R.drawable.filetype_img;
		} else if (fileType.equals("excel")) {
			return R.drawable.filetype_excel;
		} else {
			return R.drawable.filetype_others;
		}
	}

}
