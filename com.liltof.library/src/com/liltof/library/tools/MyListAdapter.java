package com.liltof.library.tools;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.com.liltof.library.PageGetter.OnSimpleActionEnd;

public class MyListAdapter extends SimpleAdapter {
	String[] _from;
	int[] _to;
	int _ressource;
	LayoutInflater _infl;
	Handler h = new Handler();
	Boolean isReady = false;
	MyListAdapter instance;
	View preparedView = null;
	int totalcount = 0;

	List<HashMap<String, String>> _data;
	SparseArray<ImageLoadNetwork> imgs = new SparseArray<ImageLoadNetwork>();
	SparseArray<View> views = new SparseArray<View>();

	public MyListAdapter(Context context, List<HashMap<String, String>> data,
			int resource, String[] from, int[] to, LayoutInflater infl,
			Boolean isRef) {
		super(context, data, resource, from, to);
		_ressource = resource;
		_from = from;
		_to = to;
		_data = data;
		_infl = infl;
		instance = this;
	}

	public void prepare(View v) {
		preparedView = v;

		for (int j = 0; j < _data.size(); j++) {

			for (int i = 0; i < _from.length; i++) {

				if (_from[i].contains("src")) {

					ImageLoadNetwork n = new ImageLoadNetwork(v.getContext(),
							true);
					n.loadImg(_data.get(j).get("src"), this,
							new OnSimpleActionEnd() {

								public Object simpleActionEnd(Object obj) {
									totalcount++;
									Log.d("PERCENT IMAGE", "" + totalcount
											+ "/" + _data.size());
									if (totalcount == _data.size())
										isReady = true;
									return null;
								}
							});
				}
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = _infl.inflate(_ressource, null);
		for (int i = 0; i < _from.length; i++) {

			if (_from[i].contains("src")) {

				ImageView img = (ImageView) vi.findViewById(_to[i]);
//				UrlImageViewHelper.setUrlDrawable(img,
//						_data.get(position).get("src"), null, null);

			} else {
				((TextView) vi.findViewById(_to[i])).setText(_data
						.get(position).get(_from[i]));
			}
		}
		return vi;
	}
}
