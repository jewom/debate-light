package com.teamgeny.debate.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debatelight.R;

public class FragmentHome extends FragmentParent {
	View me;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = inflater.inflate(R.layout.fragment_home, null);
		
		PushScale newp = (PushScale) me.findViewById(R.id.btn_new);
		newp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentNouveauProjet())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		

		PushScale about = (PushScale) me.findViewById(R.id.btn_about);
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentAbout())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		PushScale settings = (PushScale) me.findViewById(R.id.btn_settings);
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentSettings())
			                   .addToBackStack(null)
			                   .commit();
			}
		});

		return me;
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Home";
	}

}
