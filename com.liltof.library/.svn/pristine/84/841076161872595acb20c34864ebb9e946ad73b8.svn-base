package com.liltof.library.MenuListMaker;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuList {
	ListView menuLayout;
	Activity myActivity;
	ArrayList<MenuItem> items = new ArrayList<MenuItem>();
	MenuAdapter adapter = null;

	public ArrayList<MenuItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MenuItem> items) {
		this.items.clear();
		this.items.addAll(items);
		adapter.notifyDataSetChanged();
	}

	public void setOnItemClickListener(OnItemClickListener listener)
	{
		menuLayout.setOnItemClickListener(listener);
	}

	public MenuList(ListView l, Activity activity)
	{
		menuLayout = l;
		myActivity = activity;
		adapter = new MenuAdapter(items, myActivity);
		menuLayout.setAdapter(adapter);
	}
	public void addItem(MenuItem item)
	{
		items.add(item);
		adapter.notifyDataSetChanged();
	}

	public void notifyDataChanged()
	{
		adapter.notifyDataSetChanged();
	}
	public MenuItem getItem(int pos)
	{
		return items.get(pos);
	}
}
