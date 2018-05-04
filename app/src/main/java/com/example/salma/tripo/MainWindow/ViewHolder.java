package com.example.salma.tripo.MainWindow;

import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.salma.tripo.R;


public class ViewHolder {

    View view;
    TextView tripTitle;
    TextView startLocation;
    TextView endLocation;
    TextView DateTime;
    ImageView thumbnail;
//    Spinner tripType;
//
//    public Spinner getTripType() {
//
//        return tripType;
//    }

    public ViewHolder (View V )
    {
        view = V;
    }

//    public TextView getStartLocation() {
//        if(startLocation == null)
//            startLocation = (TextView) view.findViewById(R.id.startLocation);
//        return startLocation;
//    }
//
//    public TextView getEndLocation() {
//        if(endLocation == null)
//            endLocation = (TextView) view.findViewById(R.id.endLocation);
//        return endLocation;
//    }

    public TextView getDateTime() {
        if(DateTime == null)
            DateTime = (TextView) view.findViewById(R.id.DateAndTime);
        return DateTime;
    }

    public TextView getTripTitle()
    {

        if(tripTitle==null)
            tripTitle=(TextView) view.findViewById(R.id.tripTitle);
        return tripTitle;
    }


    public  ImageView getThumbnail()
    {
        if(thumbnail==null)
            thumbnail=(ImageView) view.findViewById(R.id.TripImage);
        return  thumbnail;
    }
}
