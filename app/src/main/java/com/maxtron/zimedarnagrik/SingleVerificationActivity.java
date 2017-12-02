package com.maxtron.zimedarnagrik;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class SingleVerificationActivity extends AppCompatActivity {

    TextView description;
    ImageView crash;
    Button yes, no, notsuree;
    Incidents incident;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_verification);

        incident = getIntent().getExtras().getParcelable("incident");

        description = (TextView)findViewById(R.id.des);
        crash = (ImageView) findViewById(R.id.imageclicked);

        yes = (Button)findViewById(R.id.yes);
        no = (Button)findViewById(R.id.No);
        notsuree = (Button)findViewById(R.id.NotSure);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOnServer(1);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOnServer(2);
            }
        });

        notsuree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOnServer(3);
            }
        });


    }


    public void postOnServer(int i){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("incident", incident.id);
        jsonObject.addProperty("response",i);

        Ion.with(this)
                .load("http://14084439.ngrok.io/verify/")
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        finish();
                        Toast.makeText(SingleVerificationActivity.this, "Thanks for verifying incident, We will soon get back to you", Toast.LENGTH_LONG).show();


                    }
                });


    }
}
