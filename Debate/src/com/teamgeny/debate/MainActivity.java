package com.teamgeny.debate;

import org.json.JSONObject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.fragments.FragmentHistorique;
import com.teamgeny.debate.fragments.FragmentHistoryDetails;
import com.teamgeny.debate.fragments.FragmentNouveauProjet;
import com.teamgeny.debate.fragments.FragmentViewPagerDetails;
import com.teamgeny.debate.fragments.PieFragment;

public class MainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setIcon(android.R.color.transparent);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_launcher, /* nav drawer icon to replace 'Up' caret */
		R.string.app_name, /* "open drawer" description */
		R.string.hello_world /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				getActionBar().setTitle("close");
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("open");
			}
		};
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		PushScale nouveau = (PushScale) findViewById(R.id.buttonNewProject);
		nouveau.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				FragmentManager fragmentManager = getSupportFragmentManager();
			    fragmentManager.beginTransaction()
			                   .replace(R.id.content_frame, new FragmentNouveauProjet())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		PushScale prev = (PushScale) findViewById(R.id.buttonPastProjects);
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				FragmentManager fragmentManager = getSupportFragmentManager();
			    fragmentManager.beginTransaction()
			                   .replace(R.id.content_frame, new FragmentHistorique())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		FragmentManager fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, new FragmentNouveauProjet())
	                   
	                   .commit();
	    
		
	}

	public void showFragmentHistoryDetails(String json)
	{
		FragmentViewPagerDetails f = new FragmentViewPagerDetails();
		Bundle b = new Bundle();
		b.putString("debat", json);
		f.setArguments(b);
		FragmentManager fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, f)
	                   .addToBackStack(null)
	                   .commit();
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

}
