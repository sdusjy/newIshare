package org.google.sdu.douban.isbnbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.google.sdu.douban.book.Book;
import org.google.sdu.douban.book.BookAdapter;
import org.google.sdu.douban.book.BookHandler;
import org.google.sdu.douban.book.Imageload;
import org.google.sdu.douban.book.SearchBookActivity;
import org.google.sdu.main.R;
import org.google.sdu.main.ScanDemo;
import org.google.sdu.zxing.CaptureActivity;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.renren.api.connect.android.Renren;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IsbnGetBookActivity extends Activity {
	
	private String TAG = "bookList";
	
	private ListView list_m;
	private Thread t_ready = null;
	private Handler handler;
	private BookAdapter ad;
	private static Book sharebook;
	private ProgressDialog pd;
	private ImageView logout;
    private ImageView more;
    private TextView title;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bookisbnmain);
		sharebook=new Book();
		logout=(ImageView)findViewById(R.id.header_logout);
		title=(TextView)findViewById(R.id.header_text);
		title.setText(R.string.book_search);
		more=(ImageView)findViewById(R.id.header_more);
		findViews();
		list_m.setDividerHeight(2);
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			IsbnGetBookActivity.this.finish();
			}
		});
		Intent intent=new Intent();
		intent.setClass(IsbnGetBookActivity.this, CaptureActivity.class);
		startActivityForResult(intent, 0);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		if(requestCode==0)
		{
			if (resultCode==RESULT_OK)
			{
				String num=intent.getStringExtra("RESULT");
				pd = ProgressDialog.show(this, "", "加载中");
				pd.show();
				if (isNetWork(this)) {
					String url = this.getUrl(num);
					buildList(url);
					
					handler = new Handler() {
						public void handleMessage(Message msg) {
							list_m.setAdapter(ad);
							list_m.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									String imageurl=sharebook.getImageurl();
									Bitmap bitmap=getPhotoBitmap(imageurl);
									File file = null;
									try {
										file = getMyBitmap("bit", bitmap);
									} catch (IOException e) {
										e.printStackTrace();
									}
									Renren renren = new Renren("264044add63246e5b69f6cbac47452f0","a2379286abd74dffb3bd8eeb1e019a54","204949",IsbnGetBookActivity.this);
									
									renren.publishPhoto(IsbnGetBookActivity.this, file, sharebook.getTitle());

								}
							});
							pd.dismiss();
						}
					};
				} else {
					pd.dismiss();
					new AlertDialog.Builder(this).setTitle("无网络！")
							.setMessage("手机当前无法访问移动数据网络！").create().show();
				}
				
				
				
			}
			else if(resultCode==RESULT_CANCELED) {}
		}
		else
		{
			return;
		}
	}
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (Imageload.map.size() > 1) {
			Imageload.map = new HashMap<Object, Bitmap>();
		}
	}
	private void buildList(final String url) {
		t_ready = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<Book> list = xmlResolver(url);
				if(list!=null){
					sharebook=list.get(0);
					
				}
				ad = new BookAdapter(IsbnGetBookActivity.this, list);
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
	private String getUrl(String isbn) {
		String url = "http://api.douban.com/book/subject/isbn/"+isbn;
		return url;
	}
	private void findViews() {
		
		list_m = (ListView) findViewById(R.id.search_booklist2);
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
	//创建file
	public File  getMyBitmap(String bitName,Bitmap mBitmap) throws IOException {
		File f = new File("/sdcard/Note/" + bitName + ".png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
		fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
		fOut.flush();
		} catch (IOException e) {
		e.printStackTrace();
		}
		try {
		fOut.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
		return f;
		}


}
