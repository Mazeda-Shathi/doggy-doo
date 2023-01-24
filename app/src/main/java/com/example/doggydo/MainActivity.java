package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity  {
//    FirebaseDatabase mAuth;
//    DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAuth= FirebaseDatabase.getInstance().getCurrentUser();
//        mRef=FirebaseDatabase.getInstance().getReference().child("name");
//      //  Query checkUser = mRef.orderByChild("name").equalTo(userEnteredname);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        },4500);

    }
}