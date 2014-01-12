package com.teamgeny.debate.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debatelight.R;

public class FragmentIntervenants extends FragmentParent {
	View me;
	int numIntervenant;
	String dureeDebat;
	ArrayList<Integer> listPush = new ArrayList<Integer>();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listPush.add(R.id.editText1);
		listPush.add(R.id.editText2);
		listPush.add(R.id.editText3);
		listPush.add(R.id.editText4);
		listPush.add(R.id.editText5);
		listPush.add(R.id.editText6);
		listPush.add(R.id.editText7);
		listPush.add(R.id.editText8);
		numIntervenant = getArguments().getInt("numInterv");
		me = inflater.inflate(R.layout.fragment_intervenants, null);
		for (int i = 1; i < 9; i++)
		{
			if (i <= numIntervenant)
				me.findViewById(listPush.get(i - 1)).setVisibility(View.VISIBLE);
			else
				me.findViewById(listPush.get(i - 1)).setVisibility(View.GONE);
			((EditText) me.findViewById(listPush.get(i - 1))).setImeOptions(EditorInfo.IME_ACTION_NEXT);
		}
		((PushScale)me.findViewById(R.id.pushScale1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<String> listNoms = new ArrayList<String>();
				Bundle b = getArguments();
				int num = b.getInt("numInterv");
				for (int i = 0; i < num; i++)
				{
					if (((TextView) me.findViewById(listPush.get(i))).getText().toString().length() == 0)
						listNoms.add(""+(i+1));
					else
					listNoms.add((String)((TextView) me.findViewById(listPush.get(i))).getText().toString());
				}
				b.putStringArrayList("listInterv", listNoms);
				FragmentDebat f = new FragmentDebat();

				f.setArguments(b);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
						.replace(R.id.content_frame, f, f.getTitle()).addToBackStack("back")
						.commit();
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
					      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(me.findViewById(listPush.get(0)).getWindowToken(), 0);
			}
		});
		// TODO Auto-generated method stub
		return me;
	}


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Intervenants";
	}
	
	
	
}
