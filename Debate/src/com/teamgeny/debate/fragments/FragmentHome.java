package com.teamgeny.debate.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.R;

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
		
		PushScale open = (PushScale) me.findViewById(R.id.btn_open);
		open.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentHistorique())
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
