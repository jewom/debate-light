package com.teamgeany.debate.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
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

public class FragmentDebat extends Fragment {
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
		dureeTotaleNB = Long.parseLong(t[0]) * 60 + Long.parseLong(t[1]) * 60;
		movableTotal = dureeTotaleNB;
		listInterv = getArguments().getStringArrayList("listInterv");
		me = inflater.inflate(R.layout.fragment_debat, null);
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
								long movableClean = Math.abs(movableTotal) + 2;
								
								long l = (SystemClock.elapsedRealtime() - chronometer
										.getBase()) / 1000;
								realTimeLeft.set(j, l);
								long secRemain = ((dureeTotaleNB / numIntervenant) - l);
								String sign = "";
								if (secRemain < 0)
									sign = "-";
								secRemain = Math.abs(secRemain);
								((TextView) p.findViewById(R.id.textView3))
										.setText(sign
												+ (secRemain / 60 > 9 ? secRemain / 60
														: "0" + secRemain / 60)
												+ ":"
												+ (secRemain % 60 > 9 ? secRemain % 60
														: "0" + secRemain % 60));
								Log.d("Time left", "" + "((" + dureeTotaleNB
										+ "/" + numIntervenant + ") -" + l);
								long sum = 0;
								for (int k = 0; k < realTimeLeft.size(); k++)
								{
									sum += realTimeLeft.get(k);
								}
								sum = dureeTotaleNB - sum;
								String signMov = "";
								if (sum < 0)
									signMov = "-";
								sum = Math.abs(sum);
								((TextView) me.findViewById(R.id.RemainingAll))
										.setText(signMov
												+ (sum / 60 > 9 ? sum / 60
														: "0" + sum
																/ 60)
												+ ":"
												+ (sum % 60 > 9 ? sum % 60
														: "0" + sum
																% 60));
							}
						});
				long secRemain = ((dureeTotaleNB / numIntervenant));
				((TextView) p.findViewById(R.id.textView3))
						.setText((secRemain / 60 > 9 ? secRemain / 60 : "0"
								+ secRemain / 60)
								+ ":"
								+ (secRemain % 60 > 9 ? secRemain % 60 : "0"
										+ secRemain % 60));

				timePassed.add((long) 0);
				realTimeLeft.add((long) 0);
			}

			else {
				me.findViewById(listPush.get(i - 1)).setVisibility(View.GONE);
			}

			((TextView) me.findViewById(R.id.RemainingAll))
					.setText((movableTotal / 60 > 9 ? movableTotal / 60 : "0"
							+ movableTotal / 60)
							+ ":"
							+ (movableTotal % 60 > 9 ? movableTotal % 60 : "0"
									+ movableTotal % 60));

		}
		// TODO Auto-generated method stub
		return me;
	}

}
