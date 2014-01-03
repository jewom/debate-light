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
	JSONArray debates = new JSONArray();
	Context context;
	
	public void setList() {
		((JSONAdapter) listProjects.getAdapter()).notifyDataSetChanged();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		me = inflater.inflate(R.layout.fragment_history_details, null);
		listProjects = (ListView)me.findViewById(R.id.listView1);
		
		listProjects.setAdapter(new JSONAdapter());
		
		return me;
	}
	
	public class JSONAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return debates.length();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return debates.optJSONObject(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View v = getActivity().getLayoutInflater().inflate(R.layout.item_list_history_intervenant, null);
			((TextView)v.findViewById(R.id.name)).setText(debates.optJSONObject(position).optString("name"));
			((TextView)v.findViewById(R.id.date)).setText(debates.optJSONObject(position).optString("date"));
			((TextView)v.findViewById(R.id.numInterv)).setText(""+debates.optJSONObject(position).optJSONArray("intervenants").length());

			((TextView)v.findViewById(R.id.duree)).setText(debates.optJSONObject(position).optString("duree_totale").substring(0, 5));

			// TODO Auto-generated method stub
			return v;
		}
		
	}

}
