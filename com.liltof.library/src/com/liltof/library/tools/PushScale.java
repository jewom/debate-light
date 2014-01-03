package com.liltof.library.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.liltof.library.R;

public class PushScale extends RelativeLayout {
	Animation animPush;
	Animation animRelease;
	OnClickListener OnClickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	public void setOnClickListener(OnClickListener n) {
		OnClickListener = n;
	}

	public PushScale(Context context) {
		super(context);
		init();
	}

	public PushScale(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PushScale(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if (!isInEditMode()) {
			animPush = AnimationUtils.loadAnimation(getContext(),
					R.anim.logo_push_animation);
			animRelease = AnimationUtils.loadAnimation(getContext(),
					R.anim.logo_release_animation);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();

		String DEBUG_TAG = "MOVE";
		switch (action) {
		case (MotionEvent.ACTION_DOWN):
			startAnimation(animPush);
			return true;
		case (MotionEvent.ACTION_MOVE):
			return true;
		case (MotionEvent.ACTION_UP):
			startAnimation(animRelease);
			if (event.getX() > 0 && event.getY() > 0
					&& event.getX() < getWidth() && event.getY() < getHeight()) {
				OnClickListener.onClick(this);
			} else
				Log.d("CLICK!", "OUTSIDOOO");
			return true;

		default:
			startAnimation(animRelease);
			return super.onTouchEvent(event);
		}
	}
}
