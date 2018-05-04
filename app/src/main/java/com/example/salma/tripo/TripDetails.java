package com.example.salma.tripo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.Model.Trip;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TripDetails extends AppCompatActivity {

    Button startBtn , deleteBtn , updateBtn ;
    TextView TripTitleTextView,dateTextView,timeTextView,startLocationEditText , endLocationEditText;
    List<Address> address;
    //it will be changed

    private static final int NOTIFICATION_IO_OPEN_ACTIVITY = 9;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        startBtn = (Button) findViewById(R.id.startBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        updateBtn = (Button) findViewById(R.id.updateBtn);

        startLocationEditText =  findViewById(R.id.tripStartTextView);
        endLocationEditText =  findViewById(R.id.tripEndTextView);
        TripTitleTextView = (TextView) findViewById(R.id.TripTitleTextView);

        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);


        trip = (Trip) getIntent().getSerializableExtra("myTrip");
        TripTitleTextView.setText(trip.getTripTitle());
        startLocationEditText.setText(trip.getStartLocation());
        endLocationEditText.setText(trip.getEndLocation());

        String[] parts = trip.getDateTime().split(",");
        String date = parts[0];
        String time = parts[1];


        dateTextView.setText( date);
        timeTextView.setText(time);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?saddr="+startLocationEditText.getText().toString()+"&daddr="+endLocationEditText.getText().toString()+""));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?saddr="+startLocationEditText.getText().toString()+"&daddr="+endLocationEditText.getText().toString()+""));
                startActivity(intent);

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfimationAlert();
            }
        });



        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TripDetails.this , EditTrip.class);
                i.putExtra("editTrip",trip);
                startActivity(i);

            }
        });
    }

    private void showNotification() {

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(TripDetails.this);
        NotificationManager notificationManager = (NotificationManager) TripDetails.this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+startLocationEditText.getText().toString()+"&daddr="+endLocationEditText.getText().toString()+""));
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(TripDetails.this , 0 , notifyIntent , PendingIntent.FLAG_UPDATE_CURRENT);

        notiBuilder.setContentIntent(pendingIntent);
        notiBuilder.setSmallIcon(R.drawable.googlemaps);
        notiBuilder.setAutoCancel(true);
        notiBuilder.setContentTitle("Tripo notification ");
        notiBuilder.setContentText("you have a new trip now !");

        notificationManager.notify(NOTIFICATION_IO_OPEN_ACTIVITY , notiBuilder.build());


    }

    private void showDeleteConfimationAlert() {

        //confirmation alert
        new AlertDialog.Builder(TripDetails.this)
                .setTitle("Title")
                .setMessage("Do you really want to delete this trip ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DBAdapter adapter = new DBAdapter(TripDetails.this);
                        boolean rowIsDeleted = adapter.deleteTripByTitle(TripTitleTextView.getText().toString());
                        if(rowIsDeleted)
                        {
                            Toast.makeText(TripDetails.this , "Trip deleted successfully" , Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(TripDetails.this , "something wrong happend" , Toast.LENGTH_SHORT).show();
                        }
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

//    private PlaceCoordinates getPlaceCoordinates(String place) {
//
//        Geocoder coder = new Geocoder(this);
//        PlaceCoordinates placeCoordinates;
//
//        try {
//            address = coder.getFromLocationName(place , 9);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if(address == null)
//                return null;
//
//        Address location = address.get(0);
//        placeCoordinates = new PlaceCoordinates(location.getLatitude() , location.getLongitude());
//        return placeCoordinates;
//
//    }


}
