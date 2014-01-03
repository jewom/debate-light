package com.liltof.library.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.com.liltof.library.PageGetter.OnSimpleActionEnd;

public class MyVideoView extends VideoView implements OnCompletionListener,
		OnErrorListener, OnPreparedListener {

	MediaController mc;
	public MediaController mMedia;

	public MyVideoView(Context context, AttributeSet attributes) {
		super(context, attributes);
		this.setOnPreparedListener(this);
		this.setOnCompletionListener(this);
		this.setOnErrorListener(this);

		mc = new MediaController(context, attributes);

	}

	View loaderView = null;
	OnSimpleActionEnd action = new OnSimpleActionEnd() {
		
		public Object simpleActionEnd(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public void setPreparededAction(OnSimpleActionEnd a) {
		action = a;
	}

	public void setLoaders(View l) {
		loaderView = l;
		Log.d("THROBER", "SET LOADER");
		mMedia = new MediaController(getContext());
		mMedia.setVisibility(View.INVISIBLE);
		mMedia.setMediaPlayer(this);
		mMedia.setAnchorView(loaderView);

		this.setMediaController(mMedia);
	}

	public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
		if (loaderView != null && mediaPlayer.isPlaying() == false) {
			loaderView.setVisibility(View.INVISIBLE);
			Log.d("THROBER", "ERROR : INVISIBLE");
		}
		return false;
	}

	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.d("DRAW", "SDQSD");
	}

	Boolean hasplayed = false;

	@Override
	public void setVideoURI(Uri uri) {
		// TODO Auto-generated method stub
		super.setVideoURI(uri);
		loaderView.setVisibility(View.VISIBLE);
	}
	public void onPrepared(final MediaPlayer mp) {
		start();
		hasplayed = false;

		if (mp.getDuration() > 0) {
			Log.d("NOTLIVE", "NOT LIVE");
			postDelayed(new Runnable() {

				
				public void run() {
					Log.d("NOT LIVE", "ISPLAYING?");
					if (isPlaying() && hasplayed == false) {
						hasplayed = true;
						Log.d("NOTLIVE", "PLAYING : ");
						loaderView.setVisibility(View.INVISIBLE);
						action.simpleActionEnd(mp);
					}
					else
						Log.d("NOTLIVE", "NOT PLAYING!");
						
				}
			}, 100);
		}
		else
			action.simpleActionEnd(mp);
		// Handler h = new Handler();
		// h.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		//
		// }
		// }, 3000);

		mp.setOnInfoListener(new OnInfoListener() {

			
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
				Log.d("INFO ", "WHAT : " + what + " EXTRA " + extra);
				if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
					loaderView.setVisibility(View.VISIBLE);
				else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END || what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
					loaderView.setVisibility(View.INVISIBLE);
				}
				return false;
			}
		});

	}

}