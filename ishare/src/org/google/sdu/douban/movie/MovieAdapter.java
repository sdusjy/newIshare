package org.google.sdu.douban.movie;

import java.util.ArrayList;

import org.google.sdu.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;



public class MovieAdapter extends BaseAdapter {
	private ArrayList<Movie> list;
	private Context context;

	public MovieAdapter(Context context, ArrayList<Movie> list) {
		this.list = list;
		this.context = context;
		// this.bms = bms;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Movie movie = list.get(position);
		ViewHolder holder = null;
		if (v == null) {
			LayoutInflater li = LayoutInflater.from(context);
			v = li.inflate(R.layout.list_movie_item, null);
			holder = new ViewHolder();
			holder.rb = (RatingBar) v.findViewById(R.id.rb_movie);
			holder.txt_title = (TextView) v.findViewById(R.id.text_title);
			holder.txt_director = (TextView) v.findViewById(R.id.text_director);
			holder.txt_cast = (TextView) v.findViewById(R.id.text_cast);
			holder.txt_area = (TextView) v.findViewById(R.id.text_area);
			holder.txt_date = (TextView) v.findViewById(R.id.text_date);
			holder.img = (ImageView) v.findViewById(R.id.img_movie);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.txt_title.setText(movie.getTitle());
		holder.txt_director.setText(movie.getDirector());
		holder.txt_cast.setText(movie.getCast());
		holder.txt_area.setText(movie.getArea());
		holder.txt_date.setText(movie.getReDate());
		holder.rb.setRating(movie.getRating());
		new ImageLoadTask().execute(movie.getImgUrl(), holder.img, position);
		return v;
	}

	public static class ViewHolder {
		public TextView txt_title;
		public TextView txt_director;
		public TextView txt_cast;
		public TextView txt_area;
		public TextView txt_date;
		public RatingBar rb;
		public ImageView img;
	}

	public void setList(ArrayList<Movie> list) {
		this.list = list;
	}

}
