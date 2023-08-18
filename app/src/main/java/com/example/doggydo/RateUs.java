package com.example.doggydo;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RateUs extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference   ratingReference,AvgratingReference;
    FirebaseUser mUser;

    String rating;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar); // initiate a rating bar

        Button submitButton=findViewById(R.id.ratingSubmitButtonId);
        Button CancelButton=findViewById(R.id.ratingCancelButtonId);
        mAuth= FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        rootnode = FirebaseDatabase.getInstance();
        ratingReference=rootnode.getReference("RatingPoint");
        //check if rating has been done?
        ratingReference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String rate=snapshot.child("ratingpoint").getValue(String.class);
                    float r=Float.parseFloat(rate);
                    submitButton.setBackgroundColor(000000);
                    Toast.makeText(RateUs.this, "You have already rated us", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating=String.valueOf(simpleRatingBar.getRating());
//                Float ratingNumber = simpleRatingBar.getRating();
                Toast.makeText(getApplicationContext(),"Thanks for your rating", Toast.LENGTH_LONG).show();
                saveRatingInDatabase();
                Intent intent;
                intent = new Intent(getApplicationContext(), HomePage
                        .class);
                startActivity(intent);

            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), HomePage
                        .class);
                startActivity(intent);
            }
        });


    }




//database e save
    private void saveRatingInDatabase() {
        rootnode = FirebaseDatabase.getInstance();
        ratingReference=rootnode.getReference("RatingPoint");
        //get the values
        String ratingNumber=rating.toString();
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userHelperForRating helperclass=new userHelperForRating(ratingNumber);
        ratingReference.child(uId).setValue(helperclass);
//        countRat(ratingReference,uId);





    }




}