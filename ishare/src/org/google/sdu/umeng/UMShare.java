/**
 * 
 */
package org.google.sdu.umeng;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;


import com.umeng.api.sns.UMSnsService;


/**
 * @author sjy
 *
 * @date 2012-8-13下午3:14:43
 */
public class UMShare {
	public void loginTen(Context context){
		UMSnsService.oauthTenc(context, null);
		
	}
   public void loginRenren(Context context){
	   UMSnsService.oauthRenr(context, null);
	      
   }
   public void loginSina(Context context){
	   UMSnsService.oauthSina(context, null);
	   
	   
   }
   public void shareTosina(Context context,Bitmap bitmap){
	   ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
       final byte[] picture = stream.toByteArray();
       
       if (bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
		} 
       
       UMSnsService.shareToSina(context, picture, "ͼƬ", null);
	   
   }
   public void shareToRenren(Context context,Bitmap bitmap){
	   ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
       final byte[] picture = stream.toByteArray();
       
       if (bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
		} 
       
       UMSnsService.shareToRenr(context, picture, "ͼƬ", null);
	   
   }
   public void shareToTenc(Context context,Bitmap bitmap){
	   
	   ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
       final byte[] picture = stream.toByteArray();
       
       if (bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
		} 
       
       UMSnsService.shareToTenc(context, picture, "ͼƬ", null);
	   
   }
   public void shareToall(Context context,Bitmap bitmap){
	   
	   ByteArrayOutputStream stream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
       final byte[] picture = stream.toByteArray();
       
       if (bitmap != null && !bitmap.isRecycled()){
			bitmap.recycle();
		} 
       
       UMSnsService.share(context, picture, "ͼƬ", null);
	   
   }

}
