package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutUs extends AppCompatActivity{



   // FrameLayout map;

    FirebaseUser mUser;
    WebView webView;
    Button more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        more=findViewById(R.id.moreAboutId);

        webView = findViewById(R.id.aboutUsWebview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");


  more.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent i=new Intent(AboutUs.this, postAPI.class);
          startActivity(i);
      }
  });
        findAvg();
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
                   // Toast.makeText(AboutUs.this, "avg rating", Toast.LENGTH_SHORT).show();
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