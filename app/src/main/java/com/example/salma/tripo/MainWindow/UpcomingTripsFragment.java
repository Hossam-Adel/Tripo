package com.example.salma.tripo.MainWindow;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.salma.tripo.TripDetails;

import java.util.ArrayList;


public class UpcomingTripsFragment extends Fragment{

    ListView upcomingList;
    DBAdapter dbAdapter;
    ArrayList<Trip> TripArrayList;
    String tripTitle;
    RelativeLayout defaultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcomingtrips, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        upcomingList = (ListView) getActivity().findViewById(R.id.myUpcomingTripList);
        defaultView = (RelativeLayout) getActivity().findViewById(R.id.defaultView);

        TripArrayList = new ArrayList<>();
        dbAdapter = new DBAdapter(this.getContext());

    }

    @Override
    public void onStart() {
        super.onStart();

        TripArrayList = dbAdapter.getTripsByStatus("upcoming");

        if(TripArrayList != null)
        {
            //set the static image
            for(int i = 0 ; i < TripArrayList.size() ; i++)
            {
                TripArrayList.get(i).setIdOfImage(R.drawable.earth);
            }
            TripAdapter tripAdapter = new TripAdapter(this.getContext(),R.layout.list_row,R.id.tripTitle,TripArrayList);
            upcomingList.setAdapter(tripAdapter);
            upcomingList.setVisibility(View.VISIBLE);
            defaultView.setVisibility(View.GONE);

        }else
        {
            Toast.makeText(this.getContext() , "no trips found" , Toast.LENGTH_SHORT).show();
            upcomingList.setVisibility(View.GONE);
            defaultView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        upcomingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Trip selectTrip = (Trip) upcomingList.getItemAtPosition(position);
            Trip tripData = dbAdapter.getTripByTitle(selectTrip.getTripTitle());

            Intent intent = new Intent(getContext(), TripDetails.class);
            intent.putExtra("myTrip" , tripData);
            startActivity(intent);
            }
        });
    }
}
