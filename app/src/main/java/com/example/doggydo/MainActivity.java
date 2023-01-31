package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
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

        mRef=FirebaseDatabase.getInstance().getReference().child("UserDetails");
      //  Query checkUser = mRef.orderByChild("name").equalTo(userEnteredname);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mUser!=null)
                {

                    //Toast.makeText(MainActivity.this,"dis"+s,Toast.LENGTH_SHORT).show();
                    mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                Intent intent=new Intent(MainActivity.this,HomePage.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent intent=new Intent(MainActivity.this,Profile.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
//

            else{
                Intent intent;
                intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        }},4500);

    }
}