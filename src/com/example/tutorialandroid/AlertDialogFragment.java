package com.example.tutorialandroid;

import android.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

// Alert Dialog shows up to three buttons
public class AlertDialogFragment extends DialogFragment 
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// Here we are going to have an object of type MainActivity
		// It will have to be cast to type MainActivity because getActivity
		// Normally returns an Activity. getActivity is a method inherited from
		// The Fragment class which is passed to us by the DialogFragment class
		final MainActivity main = (MainActivity) getActivity();
		
		// Builder is a nested class inside AlertDialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.gameOverTitle);
		alertDialogBuilder.setMessage(R.string.gameOverMessage);
		alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
		{
				public void onClick(DialogInterface dialog, int which) 
				{
					main.game.restart();
					main.setFigureFromGrid();
					dialog.dismiss();
				}
			});
		alertDialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						main.finish();
						dialog.dismiss();
					}
				});
		return alertDialogBuilder.create();
	}
}
