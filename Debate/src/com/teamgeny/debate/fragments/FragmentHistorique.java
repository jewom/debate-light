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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.liltof.library.tools.PreferenceManager;
import com.teamgeny.debate.MainActivity;
import com.teamgeny.debate.SwipeDismissListViewTouchListener;
import com.teamgeny.debate.SwipeDismissListViewTouchListener.DismissCallbacks;
import com.teamgeny.debatelight.R;

public class FragmentHistorique extends FragmentParent {
	View me;
	int numIntervenant;
	String dureeDebat;
	ListView listProjects;
	JSONArray debates = new JSONArray();
	Context context;

	private void populateJSON() {
		debates = new JSONArray();
		List<String> listFiles = Arrays.asList(getActivity().fileList());

		for (String e : listFiles) {
			if (e.startsWith("debat_")) {
				try {
					String content = "";
					FileInputStream file = getActivity().openFileInput(e);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(file));
					String line = null, input = "";
					while ((line = reader.readLine()) != null)
						input += line;
					reader.close();
					file.close();
					JSONObject deb = new JSONObject(input);
					String[] date = e.replace("debat_", "")
							.replace(".json", "").split("-");
					deb.put("date", date[0] + "-" + date[1] + "-" + date[2]);
					deb.put("file", e);
					Log.d("NAME", deb.toString(1));

					debates.put(deb);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		setList();
	}

	public void setList() {
		String help = PreferenceManager.getArbitraryPref(getActivity(),
				"helpProject", "");
		if (help.length() == 0 && debates.length() > 0) {
			me.findViewById(R.id.mask).setVisibility(View.VISIBLE);
			me.findViewById(R.id.mask).setOnTouchListener(
					new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							return true;
						}
					});
			me.findViewById(R.id.validMasking).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							PreferenceManager.setArbitraryPref(getActivity(),
									"helpProject", "ok");
							me.findViewById(R.id.mask).setVisibility(View.GONE);
						}
					});
		}
		((JSONAdapter) listProjects.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		me = inflater.inflate(R.layout.fragment_liste_historique, null);
		listProjects = (ListView) me.findViewById(R.id.listView1);
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(
				listProjects, new DismissCallbacks() {
					public void onDismiss(ListView listView,
							int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
							final int j = position;
							new AlertDialog.Builder(getActivity())
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setTitle(R.string.app_name)
									.setMessage(R.string.confirm_delete)
									.setPositiveButton(
											android.R.string.ok,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													if (debates
															.optJSONObject(j) != null) {
														getActivity()
																.deleteFile(
																		debates.optJSONObject(
																				j)
																				.optString(
																						"file"));
														populateJSON();
													}
												}

											})
									.setNegativeButton(android.R.string.cancel,
											null).show();

						}

					}

					@Override
					public boolean canDismiss(int position) {
						// TODO Auto-generated method stub
						return true;
					}
				});
		listProjects.setOnTouchListener(touchListener);
		listProjects.setOnScrollListener(touchListener.makeScrollListener());
		listProjects.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((MainActivity) getActivity())
						.showFragmentHistoryDetails(debates.optJSONObject(arg2)
								.toString());
			}
		});
		listProjects.setAdapter(new JSONAdapter());
		new Thread() {
			public void run() {
				populateJSON();

				Handler mainHandler = new Handler(context.getMainLooper());

				Runnable myRunnable = new Runnable() {

					@Override
					public void run() {
						setList();
					}

				}; // This is your code
				mainHandler.post(myRunnable);
			};
		}.run();
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

			View v = getActivity().getLayoutInflater().inflate(
					R.layout.item_list_projets, null);
			((TextView) v.findViewById(R.id.name)).setText(debates
					.optJSONObject(position).optString("name"));
			((TextView) v.findViewById(R.id.date)).setText(debates
					.optJSONObject(position).optString("date"));
			((TextView) v.findViewById(R.id.numInterv)).setText(""
					+ debates.optJSONObject(position)
							.optJSONArray("intervenants").length());

			((TextView) v.findViewById(R.id.duree)).setText(debates
					.optJSONObject(position).optString("duree_totale")
					.substring(0, 5));

			// TODO Auto-generated method stub
			return v;
		}

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Historique";
	}

}
