package com.example.tutorialandroid;

import android.content.Context;
import android.media.MediaPlayer;

public class Music 
{
	// The media player class is used to control the execution of audio files
	private static MediaPlayer player;
	

	public static void play(Context context, int id) 
	{
		player = MediaPlayer.create(context, id);
		// When set to true the recording plays indefinitely 
		player.setLooping(true);
		// Starts or Restarts the recording
		player.start();
	}

	public static void stop(Context context) 
	{
		if (player != null) {
			player.stop();
			// After stopping it's important to release resources
			// used by the motor of the internal player
			player.release();
			player = null;
		}
	}

}
