package com.example.salma.tripo.MainWindow;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.salma.tripo.R;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

//    public SimpleFragmentPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new UpcomingTripsFragment();
        }
        else {
            return new PastTripsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.UpComingTrips);
        } else
            return mContext.getString(R.string.PastTrips);
    }
}