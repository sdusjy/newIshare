package org.google.sdu.douban.movie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<Object, Object, Bitmap> {
	private ImageView imageView = null;
	public static Map<Object, Bitmap> map = new HashMap<Object, Bitmap>();

	@Override
	protected Bitmap doInBackground(Object... params) {
		// TODO Auto-generated method stub
		Bitmap bmp = null;
		imageView = (ImageView) params[1];
		int position = (Integer) params[2];
		try {
			if (map.size() > position) {
				bmp = map.get(Integer.toString(position));
			} else {
				bmp = BitmapFactory.decodeStream(new URL((String) params[0])
						.openStream());
				map.put(Integer.toString(position), bmp);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmp;
	}

	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
	}
}
