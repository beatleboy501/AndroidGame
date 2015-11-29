package com.example.tutorialandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class AndroidGamePreference extends Activity 
{
	public final static String PLAY_MUSIC_KEY = "music";
	public final static boolean PLAY_MUSIC_DEFAULT = true;
	public final static String PLAYER_KEY = "playerName";
	public final static String PLAYER_DEFAULT = "unspecified";
	public final static String FIGURE_KEY = "figure";
	public final static String FIGURE_DEFAULT = "completo";
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main5);
	FragmentManager fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	AndroidGamePreferenceFragment fragment = new AndroidGamePreferenceFragment();
	fragmentTransaction.replace(android.R.id.content, fragment);
	fragmentTransaction.commit();
	}
}
