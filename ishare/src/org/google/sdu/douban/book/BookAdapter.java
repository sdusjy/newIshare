package org.google.sdu.douban.book;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.google.sdu.douban.movie.ImageLoadTask;

import org.google.sdu.main.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
		holder.img.setImageBitmap(getPhotoBitmap(book.getImageurl()));
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
	private Bitmap getPhotoBitmap(String url){
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {


				myFileUrl = new URL(url);
		
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			  
			
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;	
	}
}
