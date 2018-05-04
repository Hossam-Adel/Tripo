package com.example.salma.tripo.MainWindow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.R;
import com.example.salma.tripo.Model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PastTripsFragment extends Fragment{

    ListView pastTripList;
    DBAdapter dbAdapter;
    ArrayList<Trip> TripArrayList;
    String tripTitle;
    Trip selectTrip;
    TripAdapter tripAdapter;
//    RelativeLayout defaultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pasttrips, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pastTripList = (ListView) getActivity().findViewById(R.id.myPastTripList);
//        defaultView = (RelativeLayout) getActivity().findViewById(R.id.defaultView);

        TripArrayList = new ArrayList<>();
        dbAdapter = new DBAdapter(this.getContext());
    }

    private void showDeleteConfirmationAlert(final String _tripTitle) {

//        //confirmation alert
        new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Do you really want to delete this trip ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DBAdapter adapter = new DBAdapter(getContext());
                        boolean rowIsDeleted = adapter.deleteTripByTitle(_tripTitle);
                        if(rowIsDeleted)
                        {
                            tripAdapter.remove(selectTrip);
                            tripAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext() , "Trip deleted successfully" , Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getContext() , "something wrong happend" , Toast.LENGTH_SHORT).show();
                        }
                    }})
                .setNegativeButton(android.R.string.no, null).show();



    }

    @Override
    public void onStart() {
        super.onStart();

        TripArrayList = dbAdapter.getTripsByStatus("past");



        if(TripArrayList != null)
        {
            //set the static image
            for(int i = 0 ; i < TripArrayList.size() ; i++)
            {
                TripArrayList.get(i).setIdOfImage(R.drawable.earth);
            }

             tripAdapter = new TripAdapter(this.getContext(),R.layout.list_row,R.id.tripTitle,TripArrayList);
            pastTripList.setAdapter(tripAdapter);

            pastTripList.setVisibility(View.VISIBLE);
//            defaultView.setVisibility(View.GONE);


        }else
        {
            Toast.makeText(this.getContext() , "no trips found" , Toast.LENGTH_SHORT).show();
            pastTripList.setVisibility(View.GONE);
//            defaultView.setVisibility(View.VISIBLE);
        }


//        TripArrayList = dbAdapter.getAllTrips();
//
//        if(TripArrayList != null)
//        {
//            //Toast.makeText(this.getContext() , "yes there are some trips" , Toast.LENGTH_SHORT).show();
//            //Log.i("DateTag" , "yes there are some trips ");
//            ArrayList<Trip> LastTripArrayList = new ArrayList<>();
//
//            for(int i = 0 ; i < TripArrayList.size() ; i++)
//            {
//                if(testTripDate(TripArrayList.get(i)))
//                {
//                    //Toast.makeText(this.getContext() , "yes this is a previous trip with index " + i , Toast.LENGTH_SHORT).show();
//                    //Log.i("DateTag" , "yes this is a previous trip with index " + i);
//                    TripArrayList.get(i).setIdOfImage(R.drawable.earth);
//                    LastTripArrayList.add(TripArrayList.get(i));
//                }else{
//                    //Toast.makeText(this.getContext() , "no past trips " , Toast.LENGTH_SHORT).show();
//                    Log.i("DateTag" , "no past trips ");
//                }
//
//            }
//
//            TripAdapter tripAdapter = new TripAdapter(this.getContext(),R.layout.list_row,R.id.tripTitle,LastTripArrayList);
//
//            //TripAdapter tripAdapter = new TripAdapter(this,R.layout.list_row,R.id.textView,tripArray);
//            pastTripList.setAdapter(tripAdapter);
//
//        }else
//            Toast.makeText(this.getContext() , "no trips found" , Toast.LENGTH_SHORT).show();
    }

    private boolean testTripDate(Trip trip) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        try {
            String[] DateTimeArray = trip.getDateTime().split(",");
            Date tripDate = sdf.parse(DateTimeArray[0]); //to get date
            Date currentDate = sdf.parse(String.valueOf(new Date()));

            Toast.makeText(this.getContext() , "trip date " + String.valueOf(tripDate) , Toast.LENGTH_SHORT).show();
            Toast.makeText(this.getContext() , "current date " + String.valueOf(currentDate) , Toast.LENGTH_SHORT).show();

            Log.i("DateTag" , "trip date " + String.valueOf(tripDate));
            Log.i("DateTag" , "current date " + String.valueOf(currentDate));

            if(currentDate.compareTo(tripDate) > 0)
                return false; // which means that trip date is an expired date
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        pastTripList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectTrip = (Trip) pastTripList.getItemAtPosition(position);
                tripTitle = selectTrip.getTripTitle();
                showDeleteConfirmationAlert(tripTitle);

            }
        });
    }
}
