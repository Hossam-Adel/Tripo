package com.example.salma.tripo.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.salma.tripo.NewTripActivity;

/**
 * Created by Besho on 2/14/2018.
 */

public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//intent.getStringExtra("tripTitle")
        Bundle b = intent.getExtras();
        Toast.makeText(context , "the trip title in alarm class is " + b.getString("tripTitle") , Toast.LENGTH_SHORT).show();
//        Log.e("else",  intent.getStringExtra("tripTitle"));
        Intent service_intent = new Intent(context,RingtonePlayServie.class);
        service_intent.putExtra("tripTitle" , intent.getStringExtra("tripTitle"));
        service_intent.putExtra("notes" , intent.getStringExtra("notes"));
        context.startService(service_intent);
    }
}
