package com.liltof.library.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceManager {
	
	public static void setLogin(Context ctx, String login){
		SharedPreferences userDetails = ctx.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
		Editor edit = userDetails.edit();
		edit.putString("login", login);
		edit.commit();
	}
	public static String getLogin(Context ctx)
	{
		SharedPreferences userDetails = ctx.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
		String login = userDetails.getString("login", "");
		return login;
	}
	public static void setPassword(Context ctx, String pass)
	{
		SharedPreferences userDetails = ctx.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
		Editor edit = userDetails.edit();
		edit.putString("password", pass);
		edit.commit();
	}
	public static String getPassword(Context ctx)
	{
		SharedPreferences userDetails = ctx.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
		String pass = userDetails.getString("password", "");
		return pass;
	}
}
