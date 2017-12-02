package com.maxtron.zimedarnagrik;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anish on 03/12/17.
 */

public class Incidents implements Parcelable {
    public String description;
    public String distance;
    public String imageurl;
    public String timestamp;
    int id;

    public Incidents(String description, String imageurl, String distance, String timestamp, int id){
        this.description = description;
        this.imageurl = imageurl;
        this.distance = distance;
        this.timestamp = timestamp;
        this.id = id;
    }

    protected Incidents(Parcel in) {
        description = in.readString();
        distance = in.readString();
        imageurl = in.readString();
        timestamp = in.readString();
        id = in.readInt();
    }

    public static final Creator<Incidents> CREATOR = new Creator<Incidents>() {
        @Override
        public Incidents createFromParcel(Parcel in) {
            return new Incidents(in);
        }

        @Override
        public Incidents[] newArray(int size) {
            return new Incidents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeString(distance);
        parcel.writeString(imageurl);
        parcel.writeString(timestamp);
        parcel.writeInt(id);
    }
}
