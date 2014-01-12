package com.teamgeny.debate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.fragments.FragmentAbout;
import com.teamgeny.debate.fragments.FragmentDebat;
import com.teamgeny.debate.fragments.FragmentHome;
import com.teamgeny.debate.fragments.FragmentNouveauProjet;
import com.teamgeny.debate.fragments.FragmentSettings;
import com.teamgeny.debatelight.R;

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
		R.string.app_name /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(getString(com.teamgeny.debate.R.string.app_name));
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(getString(com.teamgeny.debate.R.string.app_name));
			}
		};
		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		PushScale about = (PushScale) findViewById(R.id.buttonAbout);
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				if (checkCanLaunch() == false)
					return;
				FragmentManager fragmentManager = getSupportFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentAbout())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		PushScale nouveau = (PushScale) findViewById(R.id.buttonNewProject);
		nouveau.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				if (checkCanLaunch() == false)
					return;
				FragmentManager fragmentManager = getSupportFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentNouveauProjet())
			                   .addToBackStack(null)
			                   .commit();
			}
		});
		PushScale settings = (PushScale) findViewById(R.id.buttonSettings);
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
				if (checkCanLaunch() == false)
					return;
				FragmentManager fragmentManager = getSupportFragmentManager();
			    fragmentManager.beginTransaction()
			    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
			                   .replace(R.id.content_frame, new FragmentSettings())
			                   .addToBackStack(null)
			                   .commit();
			}
		});

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		FragmentManager fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction()
	    
	                   .replace(R.id.content_frame, new FragmentHome())
	                   
	                   .commit();
	    
		
	}

	
	public void quitDebate()
	{
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
		finish();
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
	public Boolean checkCanLaunch() {
		FragmentDebat myFragment = (FragmentDebat)getSupportFragmentManager().findFragmentByTag(new FragmentDebat().getTitle());
		
		if (myFragment != null && myFragment.isVisible() && myFragment.canQuit() == false) {
			Toast.makeText(getApplicationContext(), "VEUILLEZ TERMINER LE DEBAT D'ABORD", Toast.LENGTH_SHORT).show();
		   return false;
		}
		return true;
	}
	@Override
	public void onBackPressed() {
		if (checkCanLaunch() == false)
			return;
		super.onBackPressed();
	}

	

}
