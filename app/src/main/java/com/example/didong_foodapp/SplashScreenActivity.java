package com.example.didong_foodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SplashScreenActivity extends AppCompatActivity {
    TextView txtPhienBan;
    FusedLocationProviderClient LocationClient;
    public static int REQUEST_PERMISSION_LOCATION=1;
    SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        sharedPreferences = getSharedPreferences("location", MODE_PRIVATE);
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
        else{
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
                txtPhienBan.setText(getString(R.string.phienban)+ " "+ packageInfo.versionName);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                        finish();
                    }
                },2000);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        LocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            // Xử lý vị trí được trả về thành công
                            float latitude = (float) location.getLatitude();
                            float longitude = (float) location.getLongitude();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String sLatitude = String.valueOf(latitude);
                            String sLongitude = String.valueOf(longitude);
                            editor.putString("latitude", sLatitude);
                            editor.putString("longitude", sLongitude);
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

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.layout_flashscreen);
        txtPhienBan = (TextView) findViewById(R.id.txtVersion);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
                startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
        }
    }
}
