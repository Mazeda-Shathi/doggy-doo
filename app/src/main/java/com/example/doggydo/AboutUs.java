package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class AboutUs extends AppCompatActivity implements OnMapReadyCallback {
    boolean isPermissionGranted;


   // FrameLayout map;
    GoogleMap gmap;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        checkMyPermission();
        if (isPermissionGranted) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
            mapFragment.getMapAsync(this);

        }

    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(AboutUs.this,"Permission granted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",getPackageName(),"");
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
        findAvg();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        this.gmap=googleMap;
//        LatLng mapBangladesh=new LatLng(23.6850,90.3563);
//        this.gmap.addMarker(new MarkerOptions().position(mapBangladesh).title("Marker in Bangladesh"));
//        this.gmap.moveCamera(CameraUpdateFactory.newLatLng(mapBangladesh));
    }
    private void findAvg() {
        //find avg
        TextView showrating=findViewById(R.id.showRating);
        DatabaseReference ratingsRef = FirebaseDatabase.getInstance().getReference().child("RatingPoint") ;

        ratingsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate over each rating and calculate the average

                double totalRating = 0;
                double numRatings = 0;
                for (DataSnapshot ratingSnapshot : dataSnapshot.getChildren()) {
                    Toast.makeText(AboutUs.this, "avg rating", Toast.LENGTH_SHORT).show();
                    String rating = ratingSnapshot.child("ratingpoint").getValue(String.class);
                    double rat=Double.parseDouble(rating);
                    totalRating += rat;
                    numRatings++;

                }

                // Calculate the average rating
                double averageRating = (double) totalRating / numRatings;
                String avrRat=String.valueOf(averageRating);
                showrating.setText(avrRat);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}