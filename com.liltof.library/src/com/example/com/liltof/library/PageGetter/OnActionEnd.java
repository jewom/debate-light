package com.example.com.liltof.library.PageGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface OnActionEnd {

	public void onActionEnd(String r);
	public void onActionEndJSON(JSONObject r);
	public void onActionEndJSONArray(JSONArray r) throws JSONException;
	public void onError(String e);
}
