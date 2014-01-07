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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		View v = inflater.inflate(R.layout.fragment_piegraph, container,
				false);
		pg = (PieGraph) v.findViewById(R.id.piegraph);
		 interv = debat.optJSONArray("intervenants");
		
		for (int i = 0; i < interv.length(); i++)
		{
			PieSlice slice = new PieSlice();
			slice.setColor(Color.parseColor(colors.get(i)));
			try {
				slice.setValue(interv.getJSONObject(i).getLong("secondes_parlees"));
				pg.addSlice(slice);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		pg.setOnSliceClickedListener(new OnSliceClickedListener() {

			@Override
			public void onClick(int index) {
				PieSlice sl = pg.getSlice(index);

			}

		});

		return v;
	}
}
