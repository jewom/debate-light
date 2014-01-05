package com.teamgeny.debate.fragments;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.R;

public class FragmentDebat extends FragmentParent {
	View me;
	int numIntervenant;
	String dureeDebat;
	ArrayList<Integer> listPush = new ArrayList<Integer>();
	ArrayList<String> listInterv;
	ArrayList<Long> timePassed = new ArrayList<Long>();
	ArrayList<Boolean> activated = new ArrayList<Boolean>();
	ArrayList<Long> realTimeLeft = new ArrayList<Long>();

	long dureeTotaleNB = 0;
	long movableTotal = 0;
	int current = -1;
	OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			for (int i = 0; i < 8; i++) {
				if (v.getId() == listPush.get(i)) {

					Chronometer currentChrono = (Chronometer) v
							.findViewById(R.id.textView2);

					if (current != -1 && current != -2) {
						Chronometer c = (Chronometer) me.findViewById(
								listPush.get(current)).findViewById(
								R.id.textView2);

						timePassed.set(current,
								c.getBase() - SystemClock.elapsedRealtime());
						c.stop();
						if (i != current) {
							currentChrono.setBase(SystemClock.elapsedRealtime()
									+ timePassed.get(i));
							currentChrono.start();
						} else {
							current = -2;
							break;
						}
					} else if (current == -1) {

						currentChrono.setBase(SystemClock.elapsedRealtime());
						currentChrono.start();
					} else if (current == -2) {
						currentChrono.setBase(SystemClock.elapsedRealtime()
								+ timePassed.get(i));
						currentChrono.start();
					}

					current = i;
					Toast.makeText(getActivity(),
							"Click sur l'intervenant " + (i + 1),
							Toast.LENGTH_SHORT).show();
					break;
				}
			}

		}
	};

	public void stopAndSave() throws IOException, JSONException {
		if (current >= 0) {
			Chronometer c = (Chronometer) me
					.findViewById(listPush.get(current)).findViewById(
							R.id.textView2);
			c.stop();
		}
		long sum = 0;
		for (int k = 0; k < realTimeLeft.size(); k++) {
			sum += realTimeLeft.get(k);
		}
		// sum = dureeTotaleNB - sum;
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", getArguments().getString("name", "noname"));
			obj.put("duree_totale", formatHours(dureeTotaleNB));
			obj.put("duree_restante", formatHours(dureeTotaleNB - sum));
			obj.put("duree_parlee", formatHours(sum));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray intervenants = new JSONArray();
		for (int i = 0; i < realTimeLeft.size(); i++) {
			JSONObject in = new JSONObject();
			in.put("nom", listInterv.get(i));
			in.put("temps_parle", formatHours(realTimeLeft.get(i)));
			long secRemain = ((dureeTotaleNB / numIntervenant) - realTimeLeft
					.get(i));
			in.put("temps_restant", formatHours(secRemain));
			intervenants.put(i, in);
		}
		obj.put("intervenants", intervenants);
		// Toast.makeText(getActivity(), obj.toString(),
		// Toast.LENGTH_LONG).show();
		Log.d("JSON", obj.toString(1));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
				Locale.US);

		String FILENAME = "debat_"
				+ sdf.format(Calendar.getInstance().getTime()) + ".json";
		
		String pathdebate = FILENAME;
		// Toast.makeText(getActivity(), pathdebate, Toast.LENGTH_SHORT).show();
		FileOutputStream fos = getActivity().openFileOutput(pathdebate,
				Context.MODE_PRIVATE);
		fos.write(obj.toString().getBytes());
		fos.close();
	}

	public String formatHours(long secRemain) {
		String sign = "";
		if (secRemain < 0)
			sign = "-";
		secRemain = Math.abs(secRemain);
		long hours = secRemain / 3600;
		String textHours = "";

		textHours = (hours > 10 ? hours : "0" + hours) + ":";
		secRemain -= (hours * 3600);

		return sign + textHours
				+ (secRemain / 60 > 9 ? secRemain / 60 : "0" + secRemain / 60)
				+ ":"
				+ (secRemain % 60 > 9 ? secRemain % 60 : "0" + secRemain % 60);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		listPush.add(R.id.speaker1);
		listPush.add(R.id.speaker2);
		listPush.add(R.id.speaker3);
		listPush.add(R.id.speaker4);
		listPush.add(R.id.speaker5);
		listPush.add(R.id.speaker6);
		listPush.add(R.id.speaker7);
		listPush.add(R.id.speaker8);

		numIntervenant = getArguments().getInt("numInterv", 2);
		dureeDebat = getArguments().getString("dureeDebat", "00:01");
		String[] t = dureeDebat.split(":");
		dureeTotaleNB = Long.parseLong(t[0]) * 3600 + Long.parseLong(t[1]) * 60;
		movableTotal = dureeTotaleNB;
		listInterv = getArguments().getStringArrayList("listInterv");
		me = inflater.inflate(R.layout.fragment_debat, null);
		PushScale stop = (PushScale) me.findViewById(R.id.pushScaleTerminer);
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					stopAndSave();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		for (int i = 1; i < 9; i++) {
			if (i <= listInterv.size()) {
				me.findViewById(listPush.get(i - 1))
						.setVisibility(View.VISIBLE);
				final PushScale p = (PushScale) me.findViewById(listPush
						.get(i - 1));
				((TextView) p.findViewById(R.id.textView1)).setText(listInterv
						.get(i - 1));
				final int j = i - 1;
				p.setOnClickListener(click);
				((Chronometer) p.findViewById(R.id.textView2))
						.setOnChronometerTickListener(new OnChronometerTickListener() {

							@Override
							public void onChronometerTick(
									Chronometer chronometer) {
								movableTotal -= 1;

								long l = (SystemClock.elapsedRealtime() - chronometer
										.getBase()) / 1000;
								realTimeLeft.set(j, l);
								long secRemain = ((dureeTotaleNB / numIntervenant) - l);
								
								long percent = l * 25 / 100;
								
								if (secRemain == percent)
								{
									Toast.makeText(getActivity(), "25%", Toast.LENGTH_SHORT).show();
								}
								((TextView) p.findViewById(R.id.textView5))
										.setText(formatHours(l));
								secRemain = Math.abs(secRemain);
								((TextView) p.findViewById(R.id.textView3))
										.setText(formatHours(secRemain));
								Log.d("Time left", "" + "((" + dureeTotaleNB
										+ "/" + numIntervenant + ") -" + l);
								long sum = 0;
								for (int k = 0; k < realTimeLeft.size(); k++) {
									sum += realTimeLeft.get(k);
								}
								sum = dureeTotaleNB - sum;

								((TextView) me.findViewById(R.id.RemainingAll))
										.setText(formatHours(sum));
							}
						});
				long secRemain = ((dureeTotaleNB / numIntervenant));
				((TextView) p.findViewById(R.id.textView3))
						.setText(formatHours(secRemain));

				timePassed.add((long) 0);
				realTimeLeft.add((long) 0);
			}

			else {
				me.findViewById(listPush.get(i - 1)).setVisibility(View.GONE);
			}

			((TextView) me.findViewById(R.id.RemainingAll))
					.setText(formatHours(movableTotal));

		}
		// TODO Auto-generated method stub
		return me;
	}

}