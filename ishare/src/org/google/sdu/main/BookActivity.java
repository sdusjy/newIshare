package org.google.sdu.main;

import org.google.sdu.adapter.GridView_Adapter;

import org.google.sdu.douban.book.SearchBookActivity;
import org.google.sdu.douban.isbnbook.IsbnGetBookActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BookActivity extends Activity {
	/*
	 * @author sdusjy
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private ImageView logout;
	private ImageView more;
	private TextView  title;
	private GridView list;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.main);
        init();
        
        }
	void init(){
		logout=(ImageView)findViewById(R.id.header_logout);
		title=(TextView)findViewById(R.id.header_text);
		title.setText(R.string.book_title);
		more=(ImageView)findViewById(R.id.header_more);
		list=(GridView)findViewById(R.id.main_list);
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				BookActivity.this.finish();
			}
		});
		String []array=getResources().getStringArray(R.array.bookitem);
		int []images={R.drawable.scan,R.drawable.localbook,R.drawable.intent,R.drawable.search};
		list.setAdapter(new GridView_Adapter(this, R.layout.mainitem, array, images));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:
					Intent intent=new Intent(BookActivity.this,IsbnGetBookActivity.class);
					startActivity(intent);
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					Intent intent1=new Intent(BookActivity.this,SearchBookActivity.class);
				    startActivity(intent1);
				    break;
				}
			}
		});
	}
	
	
	
	
	
	
	
}
