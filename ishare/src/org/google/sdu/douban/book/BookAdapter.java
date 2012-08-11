package org.google.sdu.douban.book;

import java.util.ArrayList;

import org.google.sdu.douban.movie.ImageLoadTask;

import org.google.sdu.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter{
	private ArrayList<Book> list;
	private Context context;

	public BookAdapter(Context context, ArrayList<Book> list) {
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
		final Book book = list.get(position);
		ViewHolder holder = null;
		if (v == null) {
			LayoutInflater li = LayoutInflater.from(context);
			v = li.inflate(R.layout.bookitem, null);
			holder = new ViewHolder();
			holder.rb= (RatingBar) v.findViewById(R.id.rb_book);
			holder.txt_title = (TextView) v.findViewById(R.id.text_title);
			holder.txt_author = (TextView) v.findViewById(R.id.text_author);
			holder.txt_summary = (TextView) v.findViewById(R.id.text_summary);
			holder.txt_price = (TextView) v.findViewById(R.id.text_price);
			holder.txt_publish= (TextView) v.findViewById(R.id.text_publish);
			holder.txt_isbn=(TextView)v.findViewById(R.id.text_isbn);
			holder.img = (ImageView) v.findViewById(R.id.img_book);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.txt_title.setText("书名："+" "+book.getTitle());
		holder.txt_author.setText("作者："+" "+book.getAuthor());
		holder.txt_summary.setText("简介："+" "+book.getSummary());
		holder.txt_price.setText("价格："+" "+book.getPrice());
		holder.txt_publish.setText("出版社："+" "+book.getPublisher());
		holder.txt_isbn.setText("ISBN："+" "+book.getIsbn());
		holder.rb.setRating(book.getRating());
		new ImageLoadTask().execute(book.getImageurl(), holder.img, position);
		return v;
	}

	public static class ViewHolder {
		public TextView txt_title;
		public TextView txt_author;
		public TextView txt_summary;
		public TextView txt_price;
		public TextView txt_publish;
		public TextView txt_isbn;
		public RatingBar rb;
		public ImageView img;
	}

	public void setList(ArrayList<Book> list) {
		this.list = list;
	}

}
