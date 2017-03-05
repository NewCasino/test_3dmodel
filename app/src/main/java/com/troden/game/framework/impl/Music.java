package com.troden.game.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.troden.test_3dmodel.Assets;

/*___________________________________________________________________
|
| Class: Music
|
| Description:
|__________________________________________________________________*/
public class Music implements OnCompletionListener
{
	//MediaPlayer mediaPlayer;		// moved to assets
	//boolean isPrepared = false;	// moved to assets
	
	/*___________________________________________________________________
	|
	| Function: Music (constructor)
	|
	| Description: Creates a new MediaPlayer object and loads a file
	|	for playback.
	|__________________________________________________________________*/
	public Music (AssetFileDescriptor assetDescriptor)
	{
		Assets.mediaPlayer = null;
		Assets.isPrepared = false;
		Assets.mediaPlayer = new MediaPlayer ();
		try {
			Assets.mediaPlayer.setDataSource (assetDescriptor.getFileDescriptor(),
									          assetDescriptor.getStartOffset(),
											  assetDescriptor.getLength());
			Assets.mediaPlayer.prepare ();
			Assets.isPrepared = true;
			Assets.mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException ("Music(): Error loading music");
		}
	}
	
	/*___________________________________________________________________
	|
	| Function: free
	|
	| Description: Frees the Music object.
	|__________________________________________________________________*/
	public void free ()
	{
		if (Assets.mediaPlayer.isPlaying())
			Assets.mediaPlayer.stop();
		Assets.mediaPlayer.release();
	}
	
	/*___________________________________________________________________
	|
	| Function: isPlaying
	|
	| Description: Returns true if music is playing.
	|__________________________________________________________________*/
	public boolean isPlaying ()
	{
		return Assets.mediaPlayer.isPlaying ();
	}

	/*___________________________________________________________________
	|
	| Function: is Looping
	|
	| Description: Returns true if music is set to loop.
	|__________________________________________________________________*/
	public boolean isLooping ()
	{
		return Assets.mediaPlayer.isLooping ();
	}
	
	/*___________________________________________________________________
	|
	| Function: isStopped
	|
	| Description: Returns true if MediaPlayer object is stopped.
	|__________________________________________________________________*/
	public boolean isStopped ()
	{
		return (!Assets.isPrepared);
	}

	/*___________________________________________________________________
	|
	| Function: play
	|
	| Description: Starts playback.
	|__________________________________________________________________*/
	public void play ()
	{
		if (! Assets.mediaPlayer.isPlaying()) {
			try {
				synchronized(this) {
					if (!Assets.isPrepared) {
						Assets.mediaPlayer.prepare();
						Assets.isPrepared = true;	// pretty sure need to add this
					}
					Assets.mediaPlayer.start ();
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*___________________________________________________________________
	|
	| Function: setLooping
	|
	| Description: Sets music to loop or not loop.
	|__________________________________________________________________*/
	public void setLooping (boolean isLooping)
	{
		Assets.mediaPlayer.setLooping(isLooping);
	}
	
	/*___________________________________________________________________
	|
	| Function: setVolume
	|
	| Description: Sets playback volume:
	|				0 = not audible
	|				1 = full volume
	|__________________________________________________________________*/
	public void setVolume (float volume)
	{
		// Make sure between 0-1
		if (volume < 0)
			volume = 0;
		else if (volume > 1)
			volume = 1;
		Assets.mediaPlayer.setVolume(volume, volume);
	}

	/*___________________________________________________________________
	|
	| Function: stop
	|
	| Description: Stops playback if playing.
	|__________________________________________________________________*/
	public void stop ()
	{
		Assets.mediaPlayer.stop();
		synchronized(this) {
			Assets.isPrepared = false;
		}
	}
	
	/*___________________________________________________________________
	|
	| Function: onCompletion
	|
	| Description: Callback function for onCompletionListener interface.
	|	Called when playback finishes.
	|__________________________________________________________________*/
	public void onCompletion (MediaPlayer player)
	{
		synchronized(this) {
			Assets.isPrepared = false;
		}
	}
	
}
