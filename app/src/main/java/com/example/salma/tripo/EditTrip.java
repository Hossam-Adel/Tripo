package com.example.salma.tripo;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.Model.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTrip extends AppCompatActivity {
    Calendar calender = Calendar.getInstance();
    boolean dateChanged = false, timeChanged = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    Button  save, cancel;
    TextView TripTitleTextView,dateTextView,timeTextView;
    DBAdapter dbAdapter;
    EditText  TripTitleEditText,notes;
    Trip trip;
    PlaceAutocompleteFragment startPlace , endPlace;
    String endPoint, startPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        save = findViewById(R.id.savebtn);
        cancel = findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateTrip();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent y = new Intent(getApplicationContext() , TripDetails.class);
                y.putExtra("myTrip",trip);
                startActivity(y);
            }
        });
        dbAdapter = new DBAdapter(getApplicationContext());
        TripTitleTextView = (TextView) findViewById(R.id.TripTitleTextView);
        TripTitleEditText = (EditText) findViewById(R.id.TripTitleEditText);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        startPlace = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.startFrag);
        endPlace = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.endFrag);
        notes= findViewById(R.id.notes);
        trip = (Trip) getIntent().getSerializableExtra("editTrip");
        Log.i("ddd",trip.getStartLocation());
        TripTitleTextView.setText(trip.getTripTitle());
        startPlace.setText(trip.getStartLocation());
        endPlace.setText(trip.getEndLocation());

        String[] parts = trip.getDateTime().split(",");
        String date = parts[0];
        String time = parts[1];


        dateTextView.setText(date);
        timeTextView.setText(time);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });


        endPlace.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("place", (String) place.getName());
                endPoint = (String) place.getName();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place",status.getStatusMessage());
            }
        });
        startPlace.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("place", (String) place.getName());
                startPoint = (String) place.getName();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place",status.getStatusMessage());
            }
        });

    }

    private void updateTime() {
        new TimePickerDialog(this, timeSetListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true).show();

    }


    private void updateDate() {
        new DatePickerDialog(this, dateSetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();
    }
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            updateTimeLabel();
            timeChanged = true;
        }
    };
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calender.set(Calendar.YEAR, year);
            calender.set(Calendar.MONTH, monthOfYear);
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
            dateChanged = true;
        }
    };

    private void updateTimeLabel() {
        timeTextView.setText(timeFormat.format(calender.getTime()));
    }


    //update text on activity
    private void updateDateLabel() {
        dateTextView.setText(dateFormat.format(calender.getTime()));
    }
    private void updateTrip(){
        Trip newtrip = new Trip();
        newtrip.setTripTitle(TripTitleTextView.getText().toString());
        newtrip.setDateTime(dateTextView.getText().toString() + "," + timeTextView.getText().toString());
        newtrip.setNote(notes.getText().toString());
        newtrip.setStartLocation(startPoint);
        newtrip.setEndLocation(endPoint);
        int result=dbAdapter.updateTrip(newtrip,trip.getTripTitle());
        if(result>0){
            Toast.makeText(getApplicationContext(),"trip updated successfully",Toast.LENGTH_SHORT);
            Intent t = new Intent(getApplicationContext() , TripDetails.class);
            t.putExtra("myTrip",newtrip);
            startActivity(t);
        }else{
            Toast.makeText(getApplicationContext(),"failed to update trip",Toast.LENGTH_SHORT);

        }
    }
}
