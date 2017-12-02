package com.maxtron.zimedarnagrik;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ReportingActivity extends AppCompatActivity {


    Button btnup;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    double lat=0,lng=0;
    EditText des;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting);
        des = (EditText)findViewById(R.id.des);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        clickpic();

        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){

                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);//to get location
        btnup = (Button)findViewById(R.id.upload);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }


    private void clickpic() {
        // Check Camera
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                fileUri = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takePictureIntent
                        .putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, 100);
            }
        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");

            // Cursor to get image uri to display

            if (fileUri != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();

                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                ImageView imageView = (ImageView) findViewById(R.id.imageclicked);
                imageView.setImageBitmap(photo);
            }else{
                Log.i("Tag","selectedImage is null");
            }
        }else{
            finish();
        }
    }


    private void upload() {
        // Image location URL
        progressBar.setVisibility(View.VISIBLE);
        btnup.setBackgroundColor(getResources().getColor(R.color.grey));
        btnup.setEnabled(false);


//        Log.e("path", "----------------" + picturePath);
//
//        // Image
//        Bitmap bm = BitmapFactory.decodeFile(picturePath);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
//        byte[] ba = bao.toByteArray();
//        ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
//
//        Log.e("base64", "-----" + ba1);
//
//        // Upload image to server
//
//
//        JsonObject json = new JsonObject();
//        json.addProperty("lat", lat);
//        json.addProperty("lng", lng);
//        json.addProperty("description", des.getText().toString());


        //json.addProperty("timestamp", System.currentTimeMillis());

        Ion.with(this)
                .load("http://14084439.ngrok.io/report/")
                .setMultipartParameter("lat",lat+"")
                .setMultipartParameter("lng",lng+"")
                .setMultipartParameter("description",des.getText().toString())
                .setMultipartFile("image","application/image",new File(picturePath))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error

                        progressBar.setVisibility(View.GONE);
                        btnup.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        btnup.setEnabled(true);
                        finish();
                        Toast.makeText(ReportingActivity.this, "Thanks for reporting incident, We will soon get back to you", Toast.LENGTH_LONG).show();
                    }
                });

    }

}
