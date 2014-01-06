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
import android.widget.Toast;

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
		return me;
	}

	public void showSoundSelector(final String pref) throws JSONException {
//		final CharSequence[] items = { " Easy ", " Medium ", " Hard ",
//				" Very Hard " };
		final JSONArray sounds = SoundRings.listSounds();
		CharSequence[] items = new CharSequence[sounds.length()];
		String oldPref = PreferenceManager.getArbitraryPref(getActivity(), pref);
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
