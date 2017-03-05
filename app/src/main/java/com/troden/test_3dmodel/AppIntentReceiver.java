package com.troden.test_3dmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/*___________________________________________________________________
|
| Class: AppIntentReceiver
|
| Description: Receives broadcast intents from the system.
|__________________________________________________________________*/
public class AppIntentReceiver extends BroadcastReceiver
{
    /*___________________________________________________________________
    |
    | Function: onReceive
    |__________________________________________________________________*/
    @Override
    public void onReceive (Context context, Intent intent)
    {
        // Phone state changed?
        if (intent.getAction().equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            switch (telephony.getCallState()) {
                // Call started?
                case TelephonyManager.CALL_STATE_RINGING:
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //context.startService(new Intent(MyMusicService.ACTION_PHONE_PAUSE));
                    Assets.mediaPlayer.stop ();
                    Assets.isPrepared = false;
                    break;
                // Call ended?
                case TelephonyManager.CALL_STATE_IDLE:
                    //context.startService(new Intent(MyMusicService.ACTION_PHONE_RESUME));
                    // Don't need to start playing song again since that will be taken care of in OpenGLRender.onResume
                    break;
            }
        }
        // Headset disconnected?
        //else if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
        //    Toast.makeText(context, "Headphones disconnected.", Toast.LENGTH_SHORT).show();
        //    // Send an intent to our MusicService to telling it to pause the audio
        //    context.startService(new Intent(MyMusicService.ACTION_PAUSE));
        //}
    }
}
