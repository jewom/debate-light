package com.liltof.library.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.example.com.liltof.library.PageGetter.OnSimpleActionEnd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoadNetwork extends ImageView {
	private String urlDl = "";
	private Uri urlCache = null;
	private Bitmap bitmap = null;
	Context ctx;
	private String namefile = "";
	MyListAdapter myAdapt = null;
	Boolean _reflect = false;
	int heightInMemory = 100;
	int widthInMemory = 100;
	/**
	 * @param context
	 */
    public static Bitmap createReflectedImage(Context context, Bitmap originalImage) {
    	if (originalImage == null)
    		return null;
        final int reflectionGap = 4;
        
                 int width = originalImage.getWidth();
                 int height = originalImage.getHeight();
                 Matrix matrix = new Matrix();
                 matrix.preScale(1, -1);
                 Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);
                  
                                       Bitmap bitmapWithReflection = Bitmap.createBitmap(width
                   , (height + height/2), Config.ARGB_8888);
                
                Canvas canvas = new Canvas(bitmapWithReflection);
                canvas.drawBitmap(originalImage, 0, 0, null);
                Paint defaultPaint = new Paint();
                canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
                canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
                 
                Paint paint = new Paint();
                LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.CLAMP);
                paint.setShader(shader);
                paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
                canvas.drawRect(0, height, width,
                bitmapWithReflection.getHeight() + reflectionGap, paint);
                
        return bitmapWithReflection;
}
	public ImageLoadNetwork(Context context, Boolean isReflect) {
		super(context);
		ctx = context;
		_reflect = isReflect;
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ImageLoadNetwork(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
	}

	public Bitmap getImageBitmap() {
		if (bitmap == null)
		{
			bitmap = decodeSampledBitmapFromResource(getUrlCache().toString(), widthInMemory, widthInMemory);
			if (_reflect == true)
				bitmap = createReflectedImage(ctx, bitmap);
		}
		if (bitmap == null)
			loadImg(urlDl, myAdapt, null);

		return bitmap;
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ImageLoadNetwork(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ctx = context;
		// TODO Auto-generated constructor stub
	}

	public String getCachePath(String url) {
		return ctx.getCacheDir() + url.substring(url.lastIndexOf("/"));
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
	public static Bitmap decodeSampledBitmapFromResource(String res, 
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(res, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(res, options);
	}
	private void LoadImageFromWebOperations(String _url) {
		try {

			URL url = new URL(_url);
			Log.d("URL____", url.toString());
			URLConnection connection = url.openConnection();
			connection.connect();

			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(ctx.getCacheDir()
					+ namefile);

			byte data[] = new byte[204800];
			int count;
			while ((count = input.read(data)) != -1) {

				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
			bitmap = decodeSampledBitmapFromResource(getUrlCache().toString(), widthInMemory, widthInMemory);
			if (_reflect == true)
				bitmap = createReflectedImage(ctx, bitmap);
		//	bitmap = BitmapFactory.decodeFile(getUrlCache().toString());

		} catch (Exception e) {
			return;
		}

	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// if (urlCache != null)
		// setImageURI(urlCache);

		// LoadImageFromWebOperations(urlDl);
	}
	public Boolean prepare(final String url, MyListAdapter b)
	{
		Log.d("BEFORE IMG", url);
		myAdapt = b;
		b.notifyDataSetChanged();
		int index = url.lastIndexOf("/");
		if (index == -1)
			return false;
		namefile = url.substring(index);
		urlCache = Uri.parse(ctx.getCacheDir() + namefile);
		File f = new File(Uri.parse(ctx.getCacheDir() + namefile).toString());
		if (f.exists()) {
			if (bitmap == null)
				getImageBitmap();
			return false;
		}
		return true;
	}
	public void loadImg(final String url, MyListAdapter b, final OnSimpleActionEnd onSimpleActionEnd) {
		if (prepare(url, b) == false)
		{
			if (onSimpleActionEnd != null)
			onSimpleActionEnd.simpleActionEnd("");
			return;
		}
		AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
			protected void onPostExecute(String result) {

				setImageURI(urlCache);
				myAdapt.notifyDataSetChanged();
				if (onSimpleActionEnd != null)
					onSimpleActionEnd.simpleActionEnd("");
			};

			@Override
			protected String doInBackground(String... params) {
				if (url.lastIndexOf("/") > 0) {

					File f = new File(Uri.parse(ctx.getCacheDir() + namefile)
							.toString());
					if (f.exists()) {
						return "";
					}
					LoadImageFromWebOperations(url);
				}
				return "";
			}
		}.execute("");
	}

	public Uri getUrlCache() {
		return urlCache;
	}

}
