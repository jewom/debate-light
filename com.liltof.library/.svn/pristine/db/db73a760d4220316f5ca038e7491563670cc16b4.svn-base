package com.liltof.library.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Alert {
	
	static public void show(Context ctx, String titre, String message, String buttonOk, DialogInterface.OnClickListener ok) {
		// TODO Auto-generated constructor stub
		if (ok == null)
			ok = new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}


			};
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				ctx);
 
			// set title
			alertDialogBuilder.setTitle(titre);
 
			// set dialog message
			alertDialogBuilder
				.setMessage(message)
				.setCancelable(true)
				.setNegativeButton("Cancel", null)
				.setPositiveButton(buttonOk, ok);
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	static public void showList(Context ctx,String title, CharSequence[] items, DialogInterface.OnClickListener onvalid)
	{
		//final CharSequence[] items = {"Info", "Rename", "Delete"};

	    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	    builder.setTitle(title);
	    builder.setItems(items, onvalid).show();
	}
}
