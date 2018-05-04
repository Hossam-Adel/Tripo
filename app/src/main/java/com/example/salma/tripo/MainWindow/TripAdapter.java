package com.example.salma.tripo.MainWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.salma.tripo.R;
import com.example.salma.tripo.Model.Trip;

import java.util.List;

public class TripAdapter extends ArrayAdapter<Trip>{

    Context myContext;
    List<Trip> tripObjects;

    public TripAdapter(Context context, int resource, int textViewResourceId, List<Trip> objects) {
        super(context, resource, textViewResourceId, objects);
        myContext = context;
        tripObjects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
            rowView = inflater.inflate(R.layout.list_row ,parent , false);
            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.getTripTitle().setText(tripObjects.get(position).getTripTitle().toString());
//        viewHolder.getStartLocation().setText(tripObjects.get(position).getStartLocation().toString());
        viewHolder.getThumbnail().setImageResource(tripObjects.get(position).getIdOfImage());
//        viewHolder.getEndLocation().setText(tripObjects.get(position).getEndLocation().toString());
        viewHolder.getDateTime().setText(tripObjects.get(position).getDateTime().toString());

        return rowView;

    }
}
