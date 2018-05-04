package com.example.salma.tripo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.salma.tripo.Alarm.AlarmReciver;
import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.Model.Trip;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTripActivity extends AppCompatActivity {

    TextView dateText, timeText; //beshoy
    //DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    Calendar calender = Calendar.getInstance();
    Button closeBtn, saveBtn;
    EditText tripTitleEditTxt, noteEditTxt;
    DBAdapter dbAdapter;
    boolean dateChanged = false, timeChanged = false , tripTypeChoosed = false;
    Spinner tripTypeSpinner;
    String tripType;
    String startPoint = "";
    String endPoint = "";
    PlaceAutocompleteFragment startPlace , endPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        setTitle("Create a new Trip");

        //get from xml
        dateText = (TextView) findViewById(R.id.dateTextView);
        timeText = (TextView) findViewById(R.id.timeTextView);

        saveBtn = (Button) findViewById(R.id.saveBtn);
        tripTitleEditTxt = (EditText) findViewById(R.id.TripTitleEditText);
        noteEditTxt = (EditText) findViewById(R.id.noteEditText);
        tripTypeSpinner = (Spinner) findViewById(R.id.tripTypeSpinner);


        //create object from adapter which enables me to deal with SQLlite
        dbAdapter = new DBAdapter(NewTripActivity.this);

        //to set default date as current date
        calender.setTime(new Date());
        calender.set(Calendar.HOUR, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);
        String currentDate = dateFormat.format(calender.getTime());
        dateText.setText(currentDate);

        //date and time events
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate();
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTime();
            }
        });

        //close and save actions
//
        //-----------------------beshoy--
        final AlarmManager alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        final Intent myintent = new Intent(NewTripActivity.this,AlarmReciver.class);


        //--------------------------endbeshoy
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(NewTripActivity.this , "the title is " + tripTitleEditTxt.getText().toString() , Toast.LENGTH_SHORT).show();

                if (startPoint.equals("") && endPoint.equals("") && dateChanged == false && timeChanged == false && tripType.equals("unknown")) {
                    Toast.makeText(NewTripActivity.this , "incomplete trip info " , Toast.LENGTH_SHORT).show();
                }
                else{

                    saveTripInDB();

                    myintent.putExtra("tripTitle" , tripTitleEditTxt.getText().toString());
                    myintent.putExtra("notes" , noteEditTxt.getText().toString());
                     PendingIntent pendingintent = PendingIntent.getBroadcast(NewTripActivity.this,0,myintent,PendingIntent.FLAG_UPDATE_CURRENT);
                     alarm_manager.set(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),pendingintent);

                }

            }
        });


        //setup spinner
        setupSpinner();
        //Hossam part
        startPlace = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.startPlace_fragment);

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

        endPlace = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.endPlace_fragment);

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            if (tripTitleEditTxt.getText().toString().equals("") &&
                    startPoint.equals("") &&
                    endPoint.equals("") &&
                    dateChanged == false && timeChanged == false &&
                    noteEditTxt.getText().toString().equals("") && tripType.equals("unknown") && tripTypeChoosed == false) {
                finish();
            } else {
                showCloseConfirmationAlert();
            }
// back button in bar

        return super.onOptionsItemSelected(item);
    }


    private void setupSpinner() {

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter tripTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_trip_types_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        tripTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        tripTypeSpinner.setAdapter(tripTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        tripTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_oneWay))) {
                        tripType = "oneWay"; // Male
                        tripTypeChoosed = true;
                    } else if (selection.equals(getString(R.string.type_twoDirction))) {
                        tripType = "twoDirection"; // Female
                        tripTypeChoosed = true;
                    } else {
                        tripType = "unknown"; // Unknown
                        tripTypeChoosed = true;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tripType = "unknown"; // Unknown
            }
        });
    }


    private void saveTripInDB() {

        Trip trip = new Trip();
        trip.setTripTitle(tripTitleEditTxt.getText().toString());
        trip.setDateTime(dateText.getText().toString() + "," + timeText.getText().toString());
        trip.setNote(noteEditTxt.getText().toString());
        trip.setTripType(tripType);
        trip.setStartLocation(startPoint);
        trip.setEndLocation(endPoint);
        trip.setTripStatus("upcoming"); //past

        boolean success = dbAdapter.saveNewTrip(trip);

        if (success) {
            Toast.makeText(this, "new trip added successfully", Toast.LENGTH_SHORT).show();
            finish();
            dateChanged = false;
            timeChanged = false;
        } else {
            Toast.makeText(this, "something wrong happened", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTime() {
        new TimePickerDialog(this, timeSetListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true).show();

    }


    private void updateDate() {
        new DatePickerDialog(this, dateSetListener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();
    }

    //events
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

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calender.set(Calendar.MINUTE, minute);
            updateTimeLabel();
            timeChanged = true;
        }
    };

    private void updateTimeLabel() {
        timeText.setText(timeFormat.format(calender.getTime()));
    }


    //update text on activity
    private void updateDateLabel() {
        dateText.setText(dateFormat.format(calender.getTime()));
    }

    private void showCloseConfirmationAlert() {

//        //confirmation alert
        new AlertDialog.Builder(NewTripActivity.this)
                .setTitle("Cancel confirmation")
                .setMessage("Do you really want to ignore changes ?!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

}
