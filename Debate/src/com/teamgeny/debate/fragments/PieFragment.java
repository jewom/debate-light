/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.teamgeny.debate.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.teamgeny.debate.R;

public class PieFragment extends FragmentParent {
	PieGraph pg;
	ArrayList<String> colors = new ArrayList<String>();
	JSONObject debat;
	JSONArray interv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String d = getArguments().getString("debat");
		try {
			debat = new JSONObject(d);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		colors.add("#e31a1c");
		colors.add("#377db8");
		colors.add("#4daf4a");
		colors.add("#984ea3");
		colors.add("#ff7f00");
		colors.add("#ffff33");
		colors.add("#a65628");
		colors.add("#f781bf");

		View v = inflater.inflate(R.layout.fragment_piegraph, container, false);
		pg = (PieGraph) v.findViewById(R.id.piegraph);
		interv = debat.optJSONArray("intervenants");
		LinearLayout l = (LinearLayout) v.findViewById(R.id.layout_legend);
		for (int i = 0; i < interv.length(); i++) {

			PieSlice slice = new PieSlice();
			int color = Color.parseColor(colors.get(i));
			slice.setColor(Color.parseColor(colors.get(i)));
			View line = inflater.inflate(R.layout.item_graph_legend, container,
					false);
			try {
				((TextView) line.findViewById(R.id.textView1)).setText(interv
						.getJSONObject(i).getString("nom"));
				((TextView) line.findViewById(R.id.pourcentage)).setText(" "
						+ interv.getJSONObject(i).getString("pourcentage")
						+ "%");
				Drawable img = getActivity().getResources().getDrawable(
						R.drawable.rounded_white);
				img.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
				((ImageView) line.findViewById(R.id.imageView1))
						.setImageDrawable(img);
				l.addView(line);

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				if (interv.getJSONObject(i).getLong("secondes_parlees") > 0) {
					slice.setValue(interv.getJSONObject(i).getLong(
							"secondes_parlees"));

					pg.addSlice(slice);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return v;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Pie";
	}
}
