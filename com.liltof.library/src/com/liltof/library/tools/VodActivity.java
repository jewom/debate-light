package com.liltof.library.tools;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.com.liltof.library.PageGetter.OnSimpleActionEnd;
import com.liltof.library.R;
import com.vianeos.octopusfortablet.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VodActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	public static Activity goBack = null;
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 8000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = 0;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private static View throbber;
	static MyVideoView mainVideo;

	public static void stopVideo() {
		mainVideo.post(new Runnable() {
			
			public void run() {
				mainVideo.stopPlayback();
				throbber.setVisibility(View.GONE);
				mainVideo.setVisibility(View.GONE);
			}
		});

	}

	public static void playVODUrl(final String url,
			final OnSimpleActionEnd action) {
		stopVideo();
		mainVideo.post(new Runnable() {

			
			public void run() {
				mainVideo.setVisibility(View.VISIBLE);
				
				mainVideo.setVideoURI(Uri.parse(url));
				mainVideo.setPreparededAction(action);

			}
		});

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);   //new
//		    getActionBar().hide();      
		setContentView(R.layout.activity_vod);
		mainVideo = (MyVideoView) findViewById(R.id.mainvideo);
		
		throbber = findViewById(R.id.progressBarVodFull);
		mainVideo.setLoaders(throbber);

		org.json.JSONObject jdata = null;
		try {
			jdata = new JSONObject(getIntent().getExtras().getString("jData"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String titre = jdata.optString("titre_en");
		String resume = jdata.optString("resume_en");
		String age = "+" + jdata.optString("age");
		String lang = jdata.optString("langue");
		String duree = jdata.optString("duree");
		String ottUrl = jdata.optString("ott_url");
		String imagepath = jdata.optString("poster_address");
		if (imagepath != null && imagepath.startsWith("http://") == false) {
			String imgclean = imagepath.replaceAll("//", "/").replaceFirst(
					"../stb/", "");
			imagepath = getResources().getString(R.string.adresse_stb_dir) + imgclean;

		}
		throbber.setVisibility(View.VISIBLE);
		playVODUrl(ottUrl, new OnSimpleActionEnd() {

			
			public Object simpleActionEnd(Object obj) {
				mainVideo.mMedia.setVisibility(View.VISIBLE);
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
				return null;
			}
		});
		int iduree = (duree == null ? 0 : Integer.parseInt(duree));
		String dureeHM = "" + iduree / 60 + "h"
				+ Tools.formatNum(iduree % 60, 2);
		((TextView) findViewById(R.id.VodDetailTitre)).setText(titre);
		((TextView) findViewById(R.id.VodDetailResume)).setText(resume);
		((TextView) findViewById(R.id.VodDetailAge)).setText(age);
		((TextView) findViewById(R.id.VodDetailLangue)).setText(lang);
		((TextView) findViewById(R.id.VodDetailDuree)).setText(dureeHM
				.compareTo("0h00") == 0 ? "" : dureeHM);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider
				.getInstance(this, findViewById(R.id.layoutVideo), HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(
											visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
							
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		mainVideo.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				controlsView
				.animate()
				.translationY(
						0)
				.setDuration(200);
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
				
				return false;
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.

		((RelativeLayout) findViewById(R.id.layoutDescriptionVod))
				.setOnTouchListener(mDelayHideTouchListener);

		WebView img = (WebView) findViewById(R.id.webView1);
		img.setOnTouchListener(mDelayHideTouchListener);
		img.setBackgroundColor(0x00000000);
		img.loadData(
				"<html><head><style>a img, a:hover img{border: none;background: transparent !important} html,body{width:97%;height:99%}</style></head><body><center><img alt=' ' style='height:100%' src='"
						+ imagepath + "'></center></body></html>", "text/html",
				"utf-8");

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
	
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && goBack != null) {
			Intent i = new Intent(getApplicationContext(), goBack.getClass());
			startActivity(i);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		Intent i = new Intent(getApplicationContext(), MainActivity.class);
//		startActivity(i);
		
	}
}
