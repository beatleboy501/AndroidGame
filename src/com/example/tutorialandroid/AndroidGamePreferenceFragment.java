package com.example.tutorialandroid;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class AndroidGamePreferenceFragment extends PreferenceFragment 
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
