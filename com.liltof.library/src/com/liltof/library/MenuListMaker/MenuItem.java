package com.liltof.library.MenuListMaker;

import java.util.HashMap;

import android.graphics.drawable.Drawable;

public class MenuItem {
	public int item_layout_id = 0;
	public String titre = "";
	public String description = "";
	public Drawable icon = null;
	HashMap<Integer, String> Textfields = new HashMap<Integer, String>();
	HashMap<Integer, Drawable> Drawables = new HashMap<Integer, Drawable>();
	public int getItem_layout_id() {
		return item_layout_id;
	}
	public MenuItem setItem_layout_id(int item_layout_id) {
		this.item_layout_id = item_layout_id;
		
		return this;
	}
	
	public HashMap<Integer, String> getTextfields() {
		return Textfields;
	}
	public void setTextfields(HashMap<Integer, String> textfields) {
		Textfields = textfields;
	}
	public HashMap<Integer, Drawable> getDrawables() {
		return Drawables;
	}
	public void setDrawables(HashMap<Integer, Drawable> drawables) {
		Drawables = drawables;
	}
	public String getTitre() {
		return titre;
	}
	public MenuItem setTitre(String titre) {
		this.titre = titre;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public MenuItem setDescription(String description) {
		this.description = description;
		return this;
	}
	public Drawable getIcon() {
		return icon;
	}
	public MenuItem setIcon(Drawable icon) {
		this.icon = icon;
		return this;
	}
	
	@Override
	public MenuItem clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		MenuItem toReturn = new MenuItem();
		toReturn.setIcon(icon).setItem_layout_id(item_layout_id).setTitre(titre).setDescription(description);
		return toReturn;
	}
}
