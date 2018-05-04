package com.example.salma.tripo.MainWindow;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.NewTripActivity;
import com.example.salma.tripo.R;
import com.example.salma.tripo.Regiseration.LoginChoose;

public class TripActivity extends AppCompatActivity {

//    Button deleteTrip;

    public static  final String PREEFS_NAME = "myPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
//        deleteTrip = findViewById(R.id.deleteBtn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(TripActivity.this , NewTripActivity.class);
            startActivity(intent);
            }
        });

        //for view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this , getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        //for tabs
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        deleteTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(TripActivity.this , "toast " , Toast.LENGTH_SHORT).show();
////                //confirmation alert
////                new AlertDialog.Builder(TripActivity.this)
////                        .setTitle("Title")
////                        .setMessage("Do you really want to delete this trip ?")
////                        .setIcon(android.R.drawable.ic_dialog_alert)
////                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
////
////                            public void onClick(DialogInterface dialog, int whichButton) {
////                                DBAdapter adapter = new DBAdapter(TripActivity.this);
////                                boolean rowIsDeleted = adapter.deleteTripByTitle(tripTitle);
////                                if(rowIsDeleted)
////                                {
////                                    Toast.makeText(getContext() , "Trip deleted successfully" , Toast.LENGTH_SHORT).show();
////
////                                }else{
////                                    Toast.makeText(getContext() , "something wrong happend" , Toast.LENGTH_SHORT).show();
////                                }
////                            }})
////                        .setNegativeButton(android.R.string.no, null).show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_logout)
        {
            SharedPreferences.Editor editor = getSharedPreferences( PREEFS_NAME , MODE_PRIVATE).edit();
            editor.putString("loggedIn" , "false");
            editor.apply();
            Intent i = new Intent(TripActivity.this , LoginChoose.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}


// switch (item.getItemId()) {
//         // Respond to a click on the "Insert dummy data" menu option
//         case R.id.action_insert_dummy_data:
//         insertPet();
//         displayDatabaseInfo();
//         return true;
//         // Respond to a click on the "Delete all entries" menu option
//         case R.id.action_delete_all_entries:
//         // Do nothing for now
//         return true;
//         }
//         return super.onOptionsItemSelected(item);