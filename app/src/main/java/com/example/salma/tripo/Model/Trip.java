package com.example.salma.tripo.Model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Salma on 2/9/2018.
 */

public class Trip implements Serializable {

    private int idOfImage;
    private String tripTitle;
    private String startLocation;
    private String endLocation;
    private String DateTime;
    private String note;
    private String tripType;
    private String tripStatus;

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Trip(int idOfImage, String tripTitle, String startLocation, String endLocation, String dateTime , String note , String tripType , String tripStatus) {
        this.idOfImage = idOfImage;
        this.tripTitle = tripTitle;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        DateTime = dateTime;

        this.note = note;
        this.tripType = tripType;
        this.tripStatus = tripStatus;
    }

    public Trip() {

    }

    public int getIdOfImage() {
        return idOfImage;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setIdOfImage(int idOfImage) {
        this.idOfImage = idOfImage;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
