package com.liltof.library.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

public class Tools {
	public static String formatNum(int nb, int size)
	{
		String ret = ""+nb;
		if (ret.length() < size)
		{
			while (ret.length() < size)
			{
				ret = "0" + ret;
			}
		}
		return ret;
	}
	static AssetManager am;
	static List<String> mapList = null;

	/**
	 * Checks if an asset exists.
	 *
	 * @param assetName
	 * @return boolean - true if there is an asset with that name.
	 */
	public static boolean checkIfInAssets(String assetName, Context ctx) {
	    if (mapList == null) {
	        am = ctx.getAssets();
	        try {
	            mapList = Arrays.asList(am.list(""));
	        } catch (IOException e) {
	        }
	    }
	    return mapList.contains(assetName) ? true : false;
	}
}
