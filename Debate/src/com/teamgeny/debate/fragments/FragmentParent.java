package com.teamgeny.debate.fragments;

import android.support.v4.app.Fragment;

public abstract class FragmentParent extends Fragment {

		public abstract String getTitle();
		public Boolean canQuit()
		{
			
			return true;
		}
}
