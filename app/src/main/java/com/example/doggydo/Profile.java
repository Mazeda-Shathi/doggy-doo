package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private static final int REQUEST_CODE =10 ;
     TextView name,  password, email, dob, phoneNo,UserName,thana,district;
    CircleImageView Proimg;

    Button backhome,btnUpdate,btnAddImage;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;
    String fullname,em,phn,pass,birth,tha,dis;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Hooks
        Proimg=findViewById(R.id.profile_image);
        name = findViewById(R.id.ProName);
        email = findViewById(R.id.ProEmail);
        phoneNo = findViewById(R.id.ProPNum);
        password = findViewById(R.id.ProPass);
        dob = findViewById(R.id.Prodob);
        UserName=findViewById(R.id.ProUsername);
        btnAddImage=findViewById(R.id.btnAddImage);
        backhome=findViewById(R.id.backHome);
        thana=findViewById(R.id.thana);
        district=findViewById(R.id.district);


        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference().child("UserDetails");

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Profile.this,HomePage.class);
                startActivity(intent5);
            }
        });

btnAddImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in=new Intent(Profile.this,UpdateProfileImage.class);
        startActivity(in);

    }
});

    }


    @Override
//fetch data
    protected void onStart() {
        super.onStart();

         if(mUser==null)
         {
             SendUserTOLogin();
         }

         else
            {
                String uId=mUser.getUid();

                mRef.child(uId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                             fullname=snapshot.child("name").getValue(String.class);
                             em=snapshot.child("email").getValue(String.class);
                             phn=snapshot.child("phoneNumber").getValue(String.class);
                             pass=snapshot.child("password").getValue(String.class);
                             birth=snapshot.child("dateaOfBirth").getValue(String.class);
                             tha=snapshot.child("tha").getValue(String.class);
                             dis=snapshot.child("dis").getValue(String.class);
                             String uname=snapshot.child("userName").getValue(String.class);
                             String PimgUrl=snapshot.child("Profile_Image").getValue(String.class);
                           if(PimgUrl== null)
                           {
                               Proimg.setImageResource(R.drawable.user_profile);
                           }
                           else {
                               Picasso.get().load(PimgUrl).into(Proimg);
                           }

                           name.setText(fullname);
                            email.setText(em);
                           phoneNo.setText(phn);
                            password.setText(pass);
                           dob.setText(birth);
                           UserName.setText(uname);
                           thana.setText(tha);
                           district.setText(dis);






                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

                    }
                });

            }


     }

    private void SendUserTOLogin() {
        Intent in=new Intent(Profile.this,Login.class);
        startActivity(in);
    }

}