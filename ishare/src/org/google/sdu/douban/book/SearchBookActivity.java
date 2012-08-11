package org.google.sdu.douban.book;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.google.sdu.main.R;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchBookActivity extends Activity{
	private String TAG = "MovieList";
	private Button btn_q;
	private EditText edit_t;
	private ListView list_m;
	private Thread t_ready = null;
	private Handler handler;
	private BookAdapter ad;
	private ProgressDialog pd;
	private ImageView logout;
    private ImageView more;
    private TextView title;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.searchbook);
		logout=(ImageView)findViewById(R.id.header_logout);
		title=(TextView)findViewById(R.id.header_text);
		title.setText(R.string.book_search);
		more=(ImageView)findViewById(R.id.header_more);
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			SearchBookActivity.this.finish();
			}
		});
		findViews();
		list_m.setDividerHeight(2);
		pd = ProgressDialog.show(this, "", "加载中");
		pd.show();
		if (isNetWork(this)) {
			String url = this.getUrl("1", "10", "2012", "");
			buildList(url);
			btn_q.setOnClickListener(ol);
			handler = new Handler() {
				public void handleMessage(Message msg) {
					list_m.setAdapter(ad);
					pd.dismiss();
				}
			};
		} else {
			pd.dismiss();
			new AlertDialog.Builder(this).setTitle("无网络！")
					.setMessage("手机当前无法访问移动数据网络！").create().show();
		}
	}

	private OnClickListener ol = new OnClickListener() {

		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String e = edit_t.getText().toString();
			if (e == null || e.equalsIgnoreCase("")) {
				Toast.makeText(SearchBookActivity.this, "--请输入搜索内容--",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					e = URLEncoder.encode(e, "utf-8");
					pd.show();
					String url = getUrl("1", "10", "", e);
					Imageload.map = new HashMap<Object, Bitmap>();
					buildList(url);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};

	private void buildList(final String url) {
		t_ready = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<Book> list = xmlResolver(url);
				ad = new BookAdapter(SearchBookActivity.this, list);
				handler.sendMessage(handler.obtainMessage());
			}
		});
		t_ready.start();
	}

	private ArrayList<Book> xmlResolver(String urlStr) {
		ArrayList<Book> list = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		BookHandler handler = new BookHandler();
		try {
			parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();
			URL url = new URL(urlStr);
			Log.d("bookxml-urls:", urlStr);
			InputSource is = new InputSource(url.openStream());
			Log.d(TAG, "xml:" + is.toString());
			xmlreader.setContentHandler(handler);
			xmlreader.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = handler.getBookList();
		return list;
	}

	private String getUrl(String start, String end, String tag, String q) {
		String url = "http://api.douban.com/book/subjects?start-index="
				+ start + "&max-results=" + end + "&tag=" + tag + "&q=" + q;
		return url;
	}

	private void findViews() {
		btn_q = (Button) findViewById(R.id.btn_movie_query);
		edit_t = (EditText) findViewById(R.id.edit_movie_tag);
		list_m = (ListView) findViewById(R.id.search_booklist);
	}

	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (Imageload.map.size() > 1) {
			Imageload.map = new HashMap<Object, Bitmap>();
		}
	}

	public Boolean isNetWork(Context context) {
		boolean isActive = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			return isActive;
		}
		isActive = cm.getActiveNetworkInfo().isAvailable();
		return isActive;
	}

}
