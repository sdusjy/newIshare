package org.google.sdu.adapter;


import org.google.sdu.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridView_Adapter extends BaseAdapter{
	private String []array;
	private int[]images;
	private Context context;
	private LayoutInflater layoutInflater;
	private int layoutResource;
	public GridView_Adapter(Context context,int resource,String[]array,int[]images) {
		this.context=context;
		this.array=array;
		this.images=images;
		this.layoutResource = resource;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = layoutInflater.inflate(layoutResource, null);
		}
		ImageView main_imageview =(ImageView)convertView.findViewById(R.id.main_imageview);
		TextView main_Textview=(TextView)convertView.findViewById(R.id.main_textview);
		main_imageview.setBackgroundResource(images[position]);
		main_Textview.setText(array[position]);
		return convertView;
	}

}
