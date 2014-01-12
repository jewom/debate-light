package com.teamgeny.debate.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.teamgeny.debatelight.R;

public class FragmentAbout extends FragmentParent {
	View me;
	
	
	public void loadURL(String url)
	{
		Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
	}
	public void facebook(String idFB, String pseudoFB)
	{
		   try {
			    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
			     Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+idFB));
			     startActivity(i);
			   } catch (Exception e) {
				   loadURL("https://www.facebook.com/"+pseudoFB);
			   }
		
	}
	public void twitter(String pseudoTW)
	{
		loadURL("https://twitter.com/"+pseudoTW);
	}
	public void mail(String email)
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
		i.putExtra(Intent.EXTRA_SUBJECT, "Stop Smoking");

		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}


	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = inflater.inflate(R.layout.fragment_about, null);
	
		
		
		

			
		ImageView FBJ = (ImageView) me.findViewById(R.id.imageViewFbJ);
		ImageView TWJ = (ImageView) me.findViewById(R.id.imageViewTwittJ);
		ImageView EMJ = (ImageView) me.findViewById(R.id.imageViewMailJ);
		
		ImageView FBC = (ImageView) me.findViewById(R.id.imageViewFbC);
		ImageView TWC = (ImageView) me.findViewById(R.id.imageViewTwittC);
		ImageView EMC = (ImageView) me.findViewById(R.id.imageViewMailC);
		
		ImageView FBM = (ImageView) me.findViewById(R.id.imageViewFbM);
		ImageView TWM = (ImageView) me.findViewById(R.id.imageViewTwittM);
		ImageView EMM = (ImageView) me.findViewById(R.id.imageViewMailM);
		
		ImageView GOS = (ImageView) me.findViewById(R.id.imageViewSGoogle);
		ImageView FBS = (ImageView) me.findViewById(R.id.imageViewSFacebook);
		ImageView TWS = (ImageView) me.findViewById(R.id.imageViewSTwitter);
		ImageView WES = (ImageView) me.findViewById(R.id.imageViewSWeb);
		ImageView EMS = (ImageView) me.findViewById(R.id.imageViewSMail);
		
		FBJ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook("557892141", "jclamentsanz");
			}
		});
		TWJ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				twitter("clamentsanz");
			}
		});
		EMJ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mail("j.clamentsanz@gmail.com");
				
			}
		});
		
		FBC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook("774102053", "christopheromana");
			}
		});
		TWC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				twitter("liltof");
			}
		});
		EMC.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mail("christophe.romana@gmail.com");	
			}
		});
		
		FBM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook("244644702356623", "Maxime.Sattonnay");
			}
		});
		TWM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				twitter("Maximesattonnay");
			}
		});
		EMM.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mail("maxime.sattonnay@gmail.com");	
			}
		});
		
		FBS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook("359298407425011", "Team.Geny.Enterprise");
			}
		});
		TWS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				twitter("team_geny");
			}
		});
		EMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mail("contact@teamgeny.com");	
			}
		});
		WES.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.teamgeny.com"));
				startActivity(browserIntent);
			}
		});
		GOS.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setClassName("com.google.android.apps.plus",
				"com.google.android.apps.plus.phone.UrlGatewayActivity");
				intent.putExtra("customAppUri", "+Teamgeny");
				startActivity(intent);
			}
		});	
			
		

		
		
		
		return me;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "about";
	}

}
