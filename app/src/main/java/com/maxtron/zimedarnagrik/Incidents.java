package com.maxtron.zimedarnagrik;

/**
 * Created by anish on 03/12/17.
 */

public class Incidents  {
    public String description;
    public double distance;
    public String imageurl;
    public String timestamp;
    int id;

    public Incidents(String description, String imageurl, double distance, String timestamp, int id){
        this.description = description;
        this.imageurl = imageurl;
        this.distance = distance;
        this.timestamp = timestamp;
        this.id = id;
    }
}
