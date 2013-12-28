package com.teamgeany.debate.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.R;

public class FragmentIntervenants extends Fragment {
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
					listNoms.add((String)((TextView) me.findViewById(listPush.get(i))).getText().toString());
				}
				b.putStringArrayList("listInterv", listNoms);
				FragmentDebat f = new FragmentDebat();
				f.setArguments(b);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack("back")
						.commit();
			}
		});
		// TODO Auto-generated method stub
		return me;
	}
	
	
	
}
