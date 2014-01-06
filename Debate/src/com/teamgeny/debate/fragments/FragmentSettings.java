package com.teamgeny.debate.fragments;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.liltof.library.tools.PreferenceManager;
import com.teamgeny.debate.R;

public class FragmentSettings extends FragmentParent {
	View me;
	String tmp = "";
	AlertDialog soundDialog;
	MediaPlayer mediaPlayer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = inflater.inflate(R.layout.fragment_settings, null);
		
		me.findViewById(R.id.sound1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					showSoundSelector("soundend");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		((TextView) me.findViewById(R.id.actual1)).setText(PreferenceManager.getArbitraryPref(getActivity(), "soundend", SoundRings.defaultFinDebat));
		me.findViewById(R.id.sound2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					showSoundSelector("soundparoleend");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		((TextView) me.findViewById(R.id.actual2)).setText(PreferenceManager.getArbitraryPref(getActivity(), "soundparoleend", SoundRings.defaultFinUnePers));

		me.findViewById(R.id.sound3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					showSoundSelector("soundparolepercent");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		((TextView) me.findViewById(R.id.actual3)).setText(PreferenceManager.getArbitraryPref(getActivity(), "soundparolepercent", SoundRings.defaultPercent));

		SeekBar s =(SeekBar) me.findViewById(R.id.seekBar1);
		s.setProgress(Integer.parseInt(PreferenceManager.getArbitraryPref(getActivity(), "percentsounddebat", "25")));
		((TextView) me.findViewById(R.id.actual4)).setText(PreferenceManager.getArbitraryPref(getActivity(), "percentsounddebat", "25"));

		s.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				((TextView) me.findViewById(R.id.actual4)).setText(""+progress);
				PreferenceManager.setArbitraryPref(getActivity(), "percentsounddebat", ""+progress);
				
			}
		});
		return me;
	}

	public void showSoundSelector(final String pref) throws JSONException {
//		final CharSequence[] items = { " Easy ", " Medium ", " Hard ",
//				" Very Hard " };
		String def = "none";
		if (pref.contentEquals("soundparolepercent"))
			def = SoundRings.defaultPercent;
		else if (pref.contentEquals("soundparoleend"))
			def = SoundRings.defaultFinUnePers;
		else if (pref.contentEquals("soundend"))
			def = SoundRings.defaultFinDebat;
		final JSONArray sounds = SoundRings.listSounds();
		CharSequence[] items = new CharSequence[sounds.length()];
		String oldPref = PreferenceManager.getArbitraryPref(getActivity(), pref, def);
		int selected = -1;
		for (int i = 0; i < sounds.length(); i++)
		{
			items[i] = sounds.optJSONObject(i).optString("display");
			if (sounds.optJSONObject(i).optString("name").contentEquals(oldPref))
				selected = i;
		}
		// Creating and Building the Dialog
		

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("SELECTIONNEZ UNE SONNERIE");
		builder.setPositiveButton(R.string.go, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (tmp.length() != 0) {

					PreferenceManager
							.setArbitraryPref(getActivity(), pref, tmp);

				}
			}
		});
		builder.setSingleChoiceItems(items, selected,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {

					
						tmp = sounds.optJSONObject(item).optString("name");
						playBip(tmp);
						
					}
				});
		soundDialog = builder.create();
		soundDialog.show();
	}
	private void playBip(String sound) {
		if (sound.contentEquals("none"))
			return;
		int resID = getResources()
				.getIdentifier(sound, "raw", getActivity().getPackageName());
		if (mediaPlayer == null)
			mediaPlayer = MediaPlayer.create(getActivity(), resID);
		else
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = MediaPlayer.create(getActivity(), resID);
		}
		
		mediaPlayer.start();
	}

}
