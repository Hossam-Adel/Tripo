package com.example.salma.tripo.Alarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.Model.Trip;
import com.example.salma.tripo.R;
import com.example.salma.tripo.TripDetails;

public class popup extends Activity {

    Button laterBtn, startBtn, cancelTripBtn;
    private static final int NOTIFICATION_IO_OPEN_ACTIVITY = 9;
    TextView tripTitleText , tripNotes;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        laterBtn = (Button) findViewById(R.id.laterBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        cancelTripBtn = (Button) findViewById(R.id.cancelTripBtn);
        tripTitleText = (TextView) findViewById(R.id.tripTitleText);
        tripNotes = (TextView) findViewById(R.id.notes);

        //get trip title and notes
     //   Intent intent = getIntent();
        tripTitleText.setText(getIntent().getStringExtra("tripTitle"));
        Toast.makeText(popup.this , "the trip title is " + getIntent().getStringExtra("tripTitle") , Toast.LENGTH_SHORT).show();
        tripNotes.setText(getIntent().getStringExtra("notes"));

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        //buttons events
        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
                finish();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBAdapter dbAdapter = new DBAdapter(popup.this);
                trip = dbAdapter.getTripByTitle(tripTitleText.getText().toString());

//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+startLocationEditText.getText().toString()+"&daddr="+endLocationEditText.getText().toString()+""));

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartLocation()+"&daddr="+trip.getEndLocation()+""));
                startActivity(intent);
            }
        });

        cancelTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // udpdate db to past trips
            }
        });
    }


    private void showNotification() {

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(popup.this);
        NotificationManager notificationManager = (NotificationManager) popup.this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartLocation()+"&daddr="+trip.getEndLocation()+""));
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(popup.this , 0 , notifyIntent , PendingIntent.FLAG_UPDATE_CURRENT);

        notiBuilder.setContentIntent(pendingIntent);
        notiBuilder.setSmallIcon(R.drawable.googlemaps);
        notiBuilder.setAutoCancel(true);
        notiBuilder.setContentTitle("Tripo notification ");
        notiBuilder.setContentText("you have a new trip now !");

        notificationManager.notify(NOTIFICATION_IO_OPEN_ACTIVITY , notiBuilder.build());

//        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(popup.this);
//        NotificationManager notificationManager = (NotificationManager) popup.this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notifyIntent = new Intent(popup.this , MapsActivity.class);
//        notifyIntent.putExtra("startLocation" , startPlace);
//        notifyIntent.putExtra("endLocation" , endPlace);
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(popup.this , 0 , notifyIntent , PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notiBuilder.setContentIntent(pendingIntent);
//        notiBuilder.setSmallIcon(R.drawable.googlemaps);
//        notiBuilder.setAutoCancel(true);
//        notiBuilder.setContentTitle("Tripo notification ");
//        notiBuilder.setContentText("you have a new trip now !");
//
//        notificationManager.notify(NOTIFICATION_IO_OPEN_ACTIVITY , notiBuilder.build());


//    }
    }

}