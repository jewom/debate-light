package com.teamgeny.debate.fragments;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamgeny.debate.MainActivity;
import com.teamgeny.debate.R;

public class FragmentHistoryDetails extends FragmentParent {
	View me;
	int numIntervenant;
	String dureeDebat;
	ListView listProjects;
	JSONObject debat = new JSONObject();
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		String d = getArguments().getString("debat");
		try {
			debat = new JSONObject(d);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		me = inflater.inflate(R.layout.fragment_history_details, null);
		((TextView) me.findViewById(R.id.textView1)).setText(debat
				.optString("name"));
		((TextView) me.findViewById(R.id.totalparle)).setText(debat
				.optString("duree_parlee"));
		((TextView) me.findViewById(R.id.totalduree)).setText(debat
				.optString("duree_restante"));
		listProjects = (ListView) me.findViewById(R.id.listView1);

		listProjects.setAdapter(new JSONAdapter());

		return me;
	}

	public class JSONAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return debat.optJSONArray("intervenants").length();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return debat.optJSONArray("intervenants").optJSONObject(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = getActivity().getLayoutInflater().inflate(
					R.layout.item_list_history_intervenant, null);
			((TextView) v.findViewById(R.id.name)).setText(debat
					.optJSONArray("intervenants").optJSONObject(position)
					.optString("nom"));
			((TextView) v.findViewById(R.id.tempsparole)).setText(debat
					.optJSONArray("intervenants").optJSONObject(position)
					.optString("temps_parle"));
			((TextView) v.findViewById(R.id.tempsrestant)).setText(debat
					.optJSONArray("intervenants").optJSONObject(position)
					.optString("temps_restant"));

			// ((TextView)v.findViewById(R.id.duree)).setText(debates.optJSONObject(position).optString("duree_totale").substring(0,
			// 5));

			// TODO Auto-generated method stub
			return v;
		}

	}

}
