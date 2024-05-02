package com.example.didong_foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    FusedLocationProviderClient LocationClient;
    public static int REQUEST_PERMISSION_LOCATION=1;
    SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        sharedPreferences = getSharedPreferences("location",MODE_PRIVATE);
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION_LOCATION);
            return;
        }
        LocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            // Xử lý vị trí được trả về thành công
                            float latitude = (float)location.getLatitude();
                            float longitude =(float) location.getLongitude();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String sLatitude= String.valueOf(latitude);
                            String sLongitude= String.valueOf(longitude);
                            editor.putString("latitude",sLatitude);
                            editor.putString("longitude",sLongitude);
                            editor.commit();
                            Log.d("LocationFood", "sLatitude: " + sLatitude + ", sLongitude: " + sLongitude);
                        } else {
                            // Không tìm thấy vị trí nào được trả về
                            Log.e("LocationFood", "Không tìm thấy vị trí");
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý lỗi khi yêu cầu vị trí
                        Log.e("LocationFood", "Lỗi khi yêu cầu vị trí: " + e.getMessage());
                    }
                });
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        FirebaseAuth.getInstance().signOut();
        LocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    public void register(View view) {
        startActivity(new Intent(WelcomeActivity.this,RegistrationActivity.class));
    }
    public void sign(View view) {
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
    }
}