package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        mRef= FirebaseDatabase.getInstance().getReference().child("UserDetails");
      //  Query checkUser = mRef.orderByChild("name").equalTo(userEnteredname);



        if(!isInternetConnected()) {
            Toast.makeText(MainActivity.this, "You are in Offline", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, no_Internet.class);
            startActivity(intent);
            finish();
        }

//        if(!isInternetConnected(this)){
//            Toast.makeText(MainActivity.this, "no", Toast.LENGTH_SHORT).show();
//            showCustomDialog();
//        }
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    if (mUser != null) {

                        //Toast.makeText(MainActivity.this,"dis"+s,Toast.LENGTH_SHORT).show();
                        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(MainActivity.this, Profile.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }


                    else {
                        Intent intent;
                        intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 500);


    }

//    private void showCustomDialog() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage("Please connect to the internet")
//                .setCancelable(false)
//                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                })
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent in=new Intent(MainActivity.this,no_Internet.class);
//                        startActivity(in);
//                        finish();
//                    }
//                });
//    }
//
//    private boolean isInternetConnected(MainActivity mainActivity) {
//        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo wifiCon=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo MobileCon=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if((wifiCon !=null &&wifiCon.isConnected()) || (MobileCon !=null) && MobileCon.isConnected()){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }


       private boolean isInternetConnected() {
           ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
           return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();

       }



}