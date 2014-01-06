package com.teamgeny.debate.fragments;

import org.json.JSONArray;
import org.json.JSONException;

public class SoundRings {
	static final String s = "[" +
	"{'name' : 'none', 'display' : '----'},"+
	"{'name' : 'alarm_electro', 'display' : 'Alarm Electro'},"+
	"{'name' : 'beep_money', 'display' : 'Beep Money'},"+
	"{'name' : 'blip', 'display' : 'Blip'},"+
	"{'name' : 'borg', 'display' : 'Borg'},"+
	"{'name' : 'buzzer1', 'display' : 'Buzzer 1'},"+
	"{'name' : 'buzzer2', 'display' : 'Buzzer 2'},"+
	"{'name' : 'comlo', 'display' : 'Comlo'},"+
	"{'name' : 'foghi', 'display' : 'Foghi'},"+
	"{'name' : 'gare_01', 'display' : 'Gare 1'},"+
	"{'name' : 'gare_02', 'display' : 'Gare 2'},"+
	"{'name' : 'grelot', 'display' : 'Grelot'},"+
	"{'name' : 'phonering_out', 'display' : 'Phonering Out'}," +
	"{'name' : 'sifflet', 'display' : 'Sifflet'},"+
	"{'name' : 'sonar', 'display' : 'Sonar'}," +
	"{'name' : 'talkie_walkie_larsen', 'display' : 'Talkie Walkie Larsen'},"+
	"{'name' : 'till', 'display' : 'Till'},"+
	"{'name' : 'ting', 'display' : 'Ting'},"+
	"{'name' : 'velo1', 'display' : 'Velo'}"+
	"]";
	static final String defaultFinDebat = "gare_02";
	static final String defaultFinUnePers = "gare_01";
	static final String defaultPercent = "comlo";
	static JSONArray listSounds() throws JSONException
	{
		
		return new JSONArray(s);
	}
	
}
