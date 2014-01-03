package com.liltof.library.MenuListMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	ArrayList<MenuItem> myList = new ArrayList<MenuItem>();
	Activity ctx;
	public MenuAdapter(ArrayList<MenuItem> m, Activity a) {
		// TODO Auto-generated constructor stub
		myList = m;
		ctx = a;
	}




	public int getCount() {
		// TODO Auto-generated method stub
		return myList.size();
	}




	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}




	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}




	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		LayoutInflater inflater = ctx.getLayoutInflater();
        row = inflater.inflate(myList.get(position).getItem_layout_id() , parent, false);
        HashMap<Integer, String> t = myList.get(position).getTextfields();
        Set<Integer> keys =  t.keySet();
        HashMap<Integer, Drawable> d = myList.get(position).getDrawables();
        Set<Integer> keysd =  d.keySet();
        for (int i = 0; i < keys.size(); i++)
        {
        	((TextView)row.findViewById((Integer)keys.toArray()[i])).setText(t.get((Integer)keys.toArray()[i]));
        }
        for (int i = 0; i < keysd.size(); i++)
        {
        	ImageView img = (ImageView)row.
        			findViewById((Integer)keysd.toArray()[i]);
        	
        			img.setImageDrawable(d
        					.get((Integer)keysd.toArray()[i]));
        }

        	return row;
	}


	
}
