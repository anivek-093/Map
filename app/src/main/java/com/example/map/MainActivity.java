package com.example.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean mLocationPermissionGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MapActivity.LOCATION_MESSAGE);

        TextView textView = findViewById(R.id.entered_id);
        textView.setText(message);

        if(isServiceOK()){
            init();
        }
    }

    private void init() {
        Button btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getLocationPermission();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: Checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS)
        {
            Log.d(TAG, "isServiceOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this,"You can't make a request", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

//    private void getLocationPermission(){
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//                mLocationPermissionGranted = true;
//            }
//            else{
//                ActivityCompat.requestPermissions(MainActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        }
//        else{
//            ActivityCompat.requestPermissions(MainActivity.this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//
//        switch (requestCode){
//            case LOCATION_PERMISSION_REQUEST_CODE:
//                if(grantResults.length > 0) {
//                    for(int i = 0;i < grantResults.length; i++){
//                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
//                            mLocationPermissionGranted = false;
//                            return;
//                        }
//                    }
//                }
//
//        }
//    }

}