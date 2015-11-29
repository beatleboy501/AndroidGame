package com.example.tutorialandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Initial extends Activity 
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial);

	}
	
	// This is executed when the user touches the screen
	// And no other view manages the event
	public boolean onTouchEvent(MotionEvent event) 
	{
		// if the code of action is down instantiate a new Intent
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			// Pass the Intent the name of the activity filter to invoke
			// Be careful because it's a hard-coded string here
			// The intent is then passed to startActivity which invokes MainActivity
			startActivity(new Intent("com.example.tutorialandroid.MAINACTIVITY"));
		}
		return true;
	}
}
