package com.custombar.example;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.custombar.custombar.CustomBar;
import com.custombar.custombar.R;

public class ListAdapter extends ArrayAdapter<CustomInterface> {

	private List<CustomInterface> objects;

	public ListAdapter(Context context, int textViewResourceId,
			List<CustomInterface> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			convertView = vi.inflate(R.layout.row, null);

		}

		CustomInterface object = objects.get(position);

		CustomBar bar = (CustomBar) convertView.findViewById(R.id.bar);

		bar.setLeftCount(object.getLeftCount());
		bar.setRightCount(object.getRightCount());

		return convertView;
	}
}
