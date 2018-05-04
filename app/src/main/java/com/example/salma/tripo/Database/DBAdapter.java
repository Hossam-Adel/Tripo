package com.example.salma.tripo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.salma.tripo.Model.Trip;
import com.example.salma.tripo.Model.User;

import java.util.ArrayList;

public class DBAdapter {

    public static final String TABLE_NAME = "TripData";
    public static final String USER_TABLE_NAME = "userData";

    private static final String SQL_CREATE_TRIPS = "CREATE TABLE " + TABLE_NAME + " ( id INTEGER primary key AUTOINCREMENT , TITLE TEXT , STARTLOCATION TEXT , ENDLOCATION TEXT , DATETIME TEXT , NOTE TEXT , STATUS TEXT , TYPE TEXT)";
    private static final String SQL_CREATE_USERS = "CREATE TABLE " + USER_TABLE_NAME + " (EMAIL TEXT primary key, PASSWORD TEXT )";

    private static final String SQL_DELETE_TRIPS = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;

    Context myContext;
    helperClass mDbHelper;

    //constructor
    public DBAdapter(Context context)
    {
        mDbHelper = new helperClass(context);
        myContext = context;
    }


    //trip operations
    public boolean saveNewTrip(Trip trip)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentV = new ContentValues();
        contentV.put("TITLE" , trip.getTripTitle());
        contentV.put("STARTLOCATION" , trip.getStartLocation());
        contentV.put("ENDLOCATION",trip.getEndLocation());
        contentV.put("DATETIME" , trip.getDateTime());
        contentV.put("NOTE" , trip.getNote());
        contentV.put("STATUS" , trip.getTripStatus());
        contentV.put("TYPE" , trip.getTripType());

        long newRowID = db.insert(TABLE_NAME , null , contentV);

        if(newRowID != -1)
            return true;
        else
            return false;
    }
    public int updateTrip(Trip trip,String originalTitle){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentV = new ContentValues();
        contentV.put("TITLE" , trip.getTripTitle());
        contentV.put("STARTLOCATION" , trip.getStartLocation());
        contentV.put("ENDLOCATION",trip.getEndLocation());
        contentV.put("DATETIME" , trip.getDateTime());
        contentV.put("NOTE" , trip.getNote());
        int result=db.update(TABLE_NAME,contentV,"TITLE="+originalTitle,null);
        return result;
    }
    public ArrayList<Trip> getAllTrips()
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query  , null);

        if(cursor != null)
        {
            ArrayList<Trip> tripArrayList = new ArrayList<>();

            while(cursor.moveToNext())
            {
                Trip trip = new Trip();
                trip.setTripTitle(cursor.getString(0));
                trip.setStartLocation(cursor.getString(1));
                trip.setEndLocation(cursor.getString(2));
                trip.setDateTime(cursor.getString(3));
                trip.setNote(cursor.getColumnName(4));
                trip.setTripStatus(cursor.getColumnName(5));
                trip.setTripType(cursor.getColumnName(6));
                tripArrayList.add(trip);
            }

            return tripArrayList;
        }else
            return null;
    }

    public Trip getTripByTitle(String title)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE TITLE = '" + title +"'";
        Cursor cursor = db.rawQuery(query  , null);

        if(cursor != null)
        {
            cursor.moveToNext();

            Trip trip = new Trip();
            trip.setTripTitle(cursor.getString(1));
            trip.setStartLocation(cursor.getString(2));
            trip.setEndLocation(cursor.getString(3));
            trip.setDateTime(cursor.getString(4));
            trip.setNote(cursor.getColumnName(5));
            trip.setTripStatus(cursor.getColumnName(6));
            trip.setTripType(cursor.getColumnName(7));

            return trip;
        }else
            return null;
    }

    public boolean deleteTripByTitle(String title)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int effectedRows = db.delete(TABLE_NAME , "TITLE = ?" , new String[]{title});

        if(effectedRows > 0)
            return true;
        else
            return false;
    }

    public boolean deleteAllTrips()
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int effectedRows = db.delete(TABLE_NAME , null , null);

        if(effectedRows > 0)
            return true;
        else
            return false;
    }

    public ArrayList<Trip> getTripsByStatus(String status)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE STATUS = '" + status +"'";
        Cursor cursor = db.rawQuery(query  , null);

        if(cursor != null)
        {
            ArrayList<Trip> tripArrayList = new ArrayList<>();

            while(cursor.moveToNext())
            {
                Trip trip = new Trip();
                trip.setTripTitle(cursor.getString(1));
                trip.setStartLocation(cursor.getString(2));
                trip.setEndLocation(cursor.getString(3));
                trip.setDateTime(cursor.getString(4));
                trip.setNote(cursor.getString(5));
                trip.setTripStatus(cursor.getString(6));
                trip.setTripType(cursor.getString(7));
                tripArrayList.add(trip);
            }

            return tripArrayList;
        }else
            return null;
    }


    // user operations
    public boolean saveNewUser(User user)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentV = new ContentValues();
        contentV.put("EMAIL" , user.getEmail());
        contentV.put("PASSWORD" , user.getPassword());


        long newRowID = db.insert(USER_TABLE_NAME , null , contentV);

        if(newRowID != -1)
            return true;
        else
            return false;
    }


    public boolean getUserByEmailAndPassword(String email , String password)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE EMAIL = '" + email +"' AND PASSWORD = '" + password +"'";
        Cursor cursor = db.rawQuery(query  , null);

        if(cursor.moveToNext())
            return true;
        else
            return false;
    }


    public static class helperClass extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "TRIPO";

        public helperClass(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TRIPS);
            db.execSQL(SQL_CREATE_USERS);
            Log.i("test" , "onCreate called");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TRIPS);
            db.execSQL(SQL_DELETE_USERS);
            onCreate(db);
        }
    }
}
