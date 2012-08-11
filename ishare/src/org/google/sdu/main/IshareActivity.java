package org.google.sdu.main;

import org.google.sdu.adapter.GridView_Adapter;
import org.google.sdu.douban.movie.ExpDoubanMovieActivity;
import org.google.sdu.main.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class IshareActivity extends Activity {
    /** Called when the activity is first created. */
	/*
	 * @author sdusjy
	 */
   
    private GridView grid;
    private ImageView logout;
    private ImageView more;
    private TextView title;
    Builder builder;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        grid=(GridView)findViewById(R.id.main_list);
        String []array=this.getResources().getStringArray(R.array.main);
        int[]images={R.drawable.read,R.drawable.shop,R.drawable.movie,R.drawable.weather,R.drawable.news};
       grid.setAdapter(new GridView_Adapter(this, R.layout.mainitem,array,images));
        logout=(ImageView)findViewById(R.id.header_logout);
        more=(ImageView)findViewById(R.id.header_more);
        builder=new AlertDialog.Builder(this);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stu
				
				switch(position){
				case 0:
					Intent intent=new Intent();
					intent.setClass(IshareActivity.this, BookActivity.class);
					startActivity(intent);
					break;
				case 2:
					Intent intent2=new Intent(IshareActivity.this,ExpDoubanMovieActivity.class);
					startActivity(intent2);
					break;
					
				
				}
			}
		}
		);
        
       logout.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			builder
	        .setTitle("退出")
	        .setMessage("你确定要退出吗？")
	        .setIcon(R.drawable.xx)
	        .setPositiveButton("确定",
	                new DialogInterface.OnClickListener() {// 添加登出事件
	                
		                public void onClick(DialogInterface dialog,
		                        int which) {
		                
			                System.exit(0);
		                }
	                })
	        .setNegativeButton("取消",
	                new DialogInterface.OnClickListener() {// 添加取消事件
	                
		                public void onClick(DialogInterface dialog,
		                        int which) {
		                
		                }
	                }).show();
	
		}
	}) ;
        
    }
}