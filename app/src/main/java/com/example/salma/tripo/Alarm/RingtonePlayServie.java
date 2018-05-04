package com.example.salma.tripo.Alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.media.MediaPlayer;

import com.example.salma.tripo.R;

/**
 * Created by Besho on 2/14/2018.
 */

public class RingtonePlayServie extends Service {

    MediaPlayer media_song;
   

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


                media_song = MediaPlayer.create(this, R.raw.ring);
                media_song.start();

            Log.e("else", "in ringtone class "+intent.getStringExtra("tripTitle"));

            Intent in = new Intent(RingtonePlayServie.this,popup.class) ;
            in.putExtra("tripTitle" , intent.getStringExtra("tripTitle"));
            in.putExtra("notes" , intent.getStringExtra("notes"));
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);// to open popup

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("on Destroy called", "ta da");


    }

}
