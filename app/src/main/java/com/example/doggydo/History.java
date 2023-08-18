package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class History extends AppCompatActivity {



    Button prev,next;
    TextView dogClassified,probability;
    ImageView dogImage;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String useridGlobal= user.getUid();
    String classname,prob,image;


    long last,currentPage;
    long first=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        prev = findViewById(R.id.previousPageId);
        next = findViewById(R.id.nextPageId);
      //  page_no = findViewById(R.id.pageno);
        dogClassified = findViewById(R.id.predictedDogNameId);
        probability = findViewById(R.id.predictionProbabilityID);
        dogImage = findViewById(R.id.classifiedDogImageId);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("classification_history");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    last= snapshot.child(useridGlobal).getChildrenCount();
                    //for first data
                    long f=1;
                    if(snapshot.child(useridGlobal ).child(String.valueOf(f)).child("DogClass").exists()) {
                        classname = snapshot.child(useridGlobal).child(String.valueOf(f)).child("DogClass").getValue().toString();
                        prob = snapshot.child(useridGlobal).child(String.valueOf(f)).child("Probability").getValue().toString();
                        image =snapshot.child(useridGlobal).child(String.valueOf(f)).child("DogImageUri").getValue().toString();
                        dogClassified.setText(classname);
                        probability.setText(prob);
                        Picasso.get().load(image).into(dogImage);

                }
//                    else
//                    {
//                        Intent i=new Intent(History.this,noHistory.class);
//                        startActivity(i);
//                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev.setEnabled(true);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child(useridGlobal ).child(String.valueOf(first)).child("DogClass").exists())
                            {
                                classname = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("DogClass").getValue().toString();
                                prob = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("Probability").getValue().toString();
                                image = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("DogImageUri").getValue().toString();
                                dogClassified.setText(classname);
                                probability.setText(prob);
                                Picasso.get().load(image).into(dogImage);
                                Toast.makeText(History.this, ""+first, Toast.LENGTH_SHORT).show();
                                first++;
                                if(first==last+1) {
                                    first--;
                                    first--;
                                    next.setEnabled(false);
                                }




                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        // Handle possible errors.
                    }
                });


            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("DogClass").exists()) {
                                classname = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("DogClass").getValue().toString();
                                prob = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("Probability").getValue().toString();
                                image = dataSnapshot.child(useridGlobal).child(String.valueOf(first)).child("DogImageUri").getValue().toString();
                                dogClassified.setText(classname);
                                probability.setText(prob);
                                Picasso.get().load(image).into(dogImage);
                                Toast.makeText(History.this, "" + first, Toast.LENGTH_SHORT).show();
                                first--;
                                if (first == 0) {
                                    first++;
                                    prev.setEnabled(false);
                                }

                                //  page_no.setText(String.valueOf(first));




                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        // Handle possible errors.
                    }
                });


            }
        });




    }
}
