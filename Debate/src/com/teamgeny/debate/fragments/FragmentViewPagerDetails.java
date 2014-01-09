package com.teamgeny.debate.fragments;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teamgeny.debate.R;
import com.viewpagerindicator.TitlePageIndicator;

public class FragmentViewPagerDetails extends FragmentParent {
	View me;
	int numIntervenant;
	String dureeDebat;
	ListView listProjects;
	JSONObject debat = new JSONObject();
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		

		me = inflater.inflate(R.layout.fragment_viewpager_details_historique, null);
		ViewPager pager = (ViewPager) me.findViewById(R.id.pager);
		pager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
		TitlePageIndicator indicator = (TitlePageIndicator) me.findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		
		return me;
	}
	 private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
	        public ScreenSlidePagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int position) {
	        	FragmentParent f =   position == 0 ? new FragmentHistoryDetails() : new PieFragment();
	        	f.setArguments(getArguments());
	        	
	            return f;
	        }
	        @Override
	        public CharSequence getPageTitle(int position) {
	        // TODO Auto-generated method stub
	        return position == 0 ? "DETAILS" : "GRAPHIQUE";
	        }
	        @Override
	        public int getCount() {
	            return 2;
	        }
	    }
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "ViewPagerDetails";
	}
	

}
