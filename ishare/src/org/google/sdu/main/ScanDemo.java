package org.google.sdu.main;



import org.google.sdu.zxing.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScanDemo extends Activity
{	
	private EditText et;
	private Button btn;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainq);
        
        et=(EditText)this.findViewById(R.id.edittext);
        btn=(Button)this.findViewById(R.id.button);
        Intent intent=new Intent();
		intent.setClass(ScanDemo.this, CaptureActivity.class);
		startActivityForResult(intent, 0);
        btn.setOnClickListener(scan);
    }
	
	private Button.OnClickListener scan=new Button.OnClickListener()
	{
		
		public void onClick(View v)
		{
			
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		if(requestCode==0)
		{
			if (resultCode==RESULT_OK)
			{
				String num=intent.getStringExtra("RESULT");
				et.setText(num);
			}
			else if(resultCode==RESULT_CANCELED) {}
		}
		else
		{
			return;
		}
	}
}