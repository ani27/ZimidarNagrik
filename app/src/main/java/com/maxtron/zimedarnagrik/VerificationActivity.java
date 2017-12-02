package com.maxtron.zimedarnagrik;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class VerificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    double lat=0, lng=0;
    ArrayList<Incidents>incidentsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        recyclerView = (RecyclerView)findViewById(R.id.incident_list);
        progressBar = (ProgressBar)findViewById(R.id.progress);


        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){

                lat = location.getLatitude();
                lng = location.getLongitude();
                incidentsArrayList = new ArrayList<>();
                serverRequest();
            }
        };



    }

    public void serverRequest(){

        Ion.with(this)
                .load("http://14084439.ngrok.io/report/?lat="+lat+"&lng="+lng)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (result.size() > 0) {
                            // do stuff with the result or error
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject jsonObject = result.get(i).getAsJsonObject();
                                String description = jsonObject.get("description").getAsString();
                                String timestamp = jsonObject.get("timestamp").getAsString();
                                double lat_inc = jsonObject.get("lat").getAsDouble();
                                double lng_inc = jsonObject.get("lng").getAsDouble();
                                int id = jsonObject.get("id").getAsInt();
                                String url = jsonObject.get("image").getAsString();

                                double dlon = (lng - lng_inc);
                                double dlat = lat - lat_inc;

                                double a = (Math.sin(dlat / 2)) * (Math.sin(dlat / 2)) + Math.cos(lat_inc) * Math.cos(lat) * (Math.sin(dlon / 2)) * (Math.sin(dlon / 2));
                                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                                double d = 6373 * c * 1000;
                                Incidents incidents = new Incidents(description, url, d, timestamp, id);
                                incidentsArrayList.add(incidents);

                            }


                            IncidentListAdapter incidentListAdapter = new IncidentListAdapter(VerificationActivity.this, incidentsArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VerificationActivity.this);

                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(incidentListAdapter);
                            progressBar.setVisibility(View.GONE);
                        }else{
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
