package com.example.tutorialandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class MainActivity extends Activity implements OnClickListener
{	
	// The "Game" class implements the rules of the game
	// We need to declare a game object
	Game game;
	
	// Static because it's going to be associated with this class
	// Final because we don't want it to change; it's a constant (which is why it's capitalized)
	// It's 7 because the radio button grid is 7 buttons by 7 buttons
	static final int SIZE = 7;

	// These are button id's that are also defined in the xml
	// It's a 7x7 array for the radio buttons
	private final int ids [][] = 
		{
			{0, 0, R.id.f1, R.id.f2, R.id.f3, 0, 0},
			{0, 0, R.id.f4, R.id.f5, R.id.f6, 0, 0},
			{R.id.f7, R.id.f8, R.id.f9, R.id.f10, R.id.f11, R.id.f12, R.id.f13},
			{R.id.f14, R.id.f15, R.id.f16, R.id.f17, R.id.f18, R.id.f19, R.id.f20},
			{R.id.f21, R.id.f22, R.id.f23, R.id.f24, R.id.f25, R.id.f26, R.id.f27},
			{0, 0, R.id.f28, R.id.f29, R.id.f30, 0, 0},
			{0, 0, R.id.f31, R.id.f32, R.id.f33, 0, 0}
		};
	
	// The menu graphic is "inflated" in this method
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case R.id.menuAbout:
		startActivity(new Intent(this, About.class));
		return true;
		case R.id.sendMessage:		
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, "Score");
			intent.putExtra(Intent.EXTRA_TEXT, "Hello ..., I've scored ... points in Jump The Peg ...");
			startActivity(intent);
		return true;
		case R.id.preferences:
			startActivity(new Intent(this, AndroidGamePreference.class));
		return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void log(String text)
	{
		Log.d("LifeCycleTest", text);
	}
	
	// This method overrides the method in class "Activity"
	// It takes a "bundle" that contains the saved instance state as a parameter
	// A bundle is the state of the views saved in key-value pairs
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Uses the "onCreate" method from the superclass, which is mandatory
		// onCreate is not called explicitly, but it is executed automatically
		// in certain moments in the life cycle of the activity.
		super.onCreate(savedInstanceState);
		
		// Outputs the content to a view in the activity 
		setContentView(R.layout.activity_main5);
		
		// in LogCat this message will appear if the onCreate method is called
		log("created");
		
		// register the activity as a listener for all the buttons including the central
		registerListeners();
		
		// Instantiate an object of type "Game"
		game = new Game();
		
		// Put the initial layout
		setFigureFromGrid();
	}
	
	public void onStart()
	{
		super.onStart();
		log("started");
	}
	
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		String grid = savedInstanceState.getString("GRID");
		game.stringToGrid(grid);
		setFigureFromGrid();
		log("onRestoreInstanceState() called");
	}
	
	protected void onResume()
	{
		super.onResume();
		Boolean play = false;
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
				if (sharedPreferences.contains(AndroidGamePreference.PLAY_MUSIC_KEY))
				play = sharedPreferences.getBoolean(AndroidGamePreference.PLAY_MUSIC_KEY,
						AndroidGamePreference.PLAY_MUSIC_DEFAULT);
				if (play == true)
				Music.play(this, R.raw.sampleaudio);
	}
	
	protected void onPause()
	{
		super.onPause();
		// stop the music when the system is about to launch another activity
		Music.stop(this);
		log("paused");
	}
	
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putString("GRID", game.gridToString());
		super.onSaveInstanceState(outState);
		log("onSaveInstanceState() called");
	}
	
	protected void onStop()
	{
		super.onStop();
		log("stopped");
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
		log("destroyed");
	}
	
	protected void onRestart()
	{
		super.onRestart();
		log("restarted");
	}
	
	//
	private void registerListeners() 
	{
		RadioButton button;
		
		// For each column
		for(int i=0; i<SIZE; i++)
		{
			// For each row
			for(int j=0; j<SIZE; j++)
			{
				// If the button ids are not the first in the array
				if(ids[i][j]!=0)	
				{
					// Cast the View (found by ints passed into for loops) to a radio button
					button = (RadioButton) findViewById(ids[i][j]);
					
					// Register a callback to be invoked when
					// this view is pressed on the screen 
					// (This method will be called inside "onCreate")
					// remember the view has been cast to a radio button
					button.setOnClickListener(this);
				}
			}
		}
	 }
	
	// This method identifies the coordinates of the button pushed
	// it passes them to the "play" method which updates the array "grid"
	// 
	@Override
	public void onClick(View v) 
	{		
		// once again, cast the view to a RadioButton and get its id
		int id = ((RadioButton) v).getId();
		
		// for each column and row permutation
		for (int i=0; i<SIZE; i++)
		{
			for (int j=0; j<SIZE; j++)
			{
				
				// if the the ints are equal to a button id
				if (ids[i][j] == id) 
				{
					// store the variables and determine whether ready to pick or drop
					game.play(i, j);
					break;
				}
			}
		}
		
		// redraw the the radio button grid 
		setFigureFromGrid();
		
		// if the game is finished show a message
		if (game.isGameFinished())
		{
			// Toast is a floating window that quickly displays a small message
			// Takes in a context, some message, and a duration
			new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
		}		
	}
	
	// the initial state of the radio buttons 
	public void setFigureFromGrid ()
	{
		RadioButton button;
		for (int i=0; i<SIZE; i++)
		{
			for (int j=0; j<SIZE; j++)
			{
				if (ids[i][j] != 0)
				{
					int value = game.getGrid(i, j);
					button = (RadioButton) findViewById(ids[i][j]);
					// use the information from the "grid" member of "Game"
					// a 7x7 array, 1 indicates the position is occupied
					// 0 indicates the position is empty
					if (value == 1)
					{
						button.setChecked(true);
					}
					else
					{
						button.setChecked(false);
					}
				}
			}
		}
	}
}
