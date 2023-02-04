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
    EditText name, password, email, dob, phoneNo,UserName;
    CircleImageView Proimg;
    TextInputLayout addImageText;
    Button backhome,btnUpdate;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;
    String fullname,em,phn,pass,birth;
    Uri imguri;
   FirebaseStorage storage;
   StorageReference storageRef;
   ProgressDialog mloadingBar;

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
        btnUpdate=findViewById(R.id.btnUpdate);
        backhome=findViewById(R.id.backHome);
        //addImageText=findViewById(R.id.addimageText);

        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference().child("UserDetails");
        storage= FirebaseStorage.getInstance();
        storageRef=storage.getReference().child("Profile_Image");

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Profile.this,HomePage.class);
                startActivity(intent5);
            }
        });
        //take image
        Proimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();
//
            storageRef.child(uId).putFile(imguri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    storageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                            mloadingBar.setTitle("Wait");
//                mloadingBar.setCanceledOnTouchOutside(false);
//                mloadingBar.show();
                            HashMap hashMap=new HashMap();
                            hashMap.put("Profile_Image",uri.toString());

                           // hashMap.put("name",name);
                          //  Toast.makeText(Profile.this,"name "+name,Toast.LENGTH_SHORT).show();




                            mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
//                                    mloadingBar.dismiss();
                                    Toast.makeText(Profile.this,"img added",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Profile.this,HomePage.class);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(Profile.this,"img failed",Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    });


                }
            });
            }
        });

    }


    @Override
    //for image
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            imguri=data.getData();
            Proimg.setImageURI(imguri);
        }
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
              // Toast.makeText(Profile.this,"id "+uId,Toast.LENGTH_SHORT).show();

                mRef.child(uId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                             fullname=snapshot.child("name").getValue(String.class);
                             em=snapshot.child("email").getValue(String.class);
                             phn=snapshot.child("phoneNumber").getValue(String.class);
                             pass=snapshot.child("password").getValue(String.class);
                             birth=snapshot.child("dateaOfBirth").getValue(String.class);
                             String uname=snapshot.child("userName").getValue(String.class);
                             String PimgUrl=snapshot.child("Profile_Image").getValue(String.class);

                            name.setText(fullname);
                            email.setText(em);
                           phoneNo.setText(phn);
                            password.setText(pass);
                           dob.setText(birth);
                           UserName.setText(uname);

                          //  Toast.makeText(Profile.this,PimgUrl,Toast.LENGTH_SHORT).show();
                            Picasso.get().load(PimgUrl).into(Proimg);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Profile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

                    }
                });
//                mRefference.child(uId).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        if(snapshot.exists()){
//
//                            String PimgUrl=snapshot.child("Profile_Image").getValue(String.class);
//                            Toast.makeText(Profile.this,"image url "+PimgUrl,Toast.LENGTH_SHORT).show();
//                            Picasso.get().load(PimgUrl).into(ProImg2);
//
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(Profile.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
            }


     }

    private void SendUserTOLogin() {
        Intent in=new Intent(Profile.this,Login.class);
        startActivity(in);
    }

    //    private void showAllUserData() {
//        Intent intent = getIntent();
//
//        String user_Full_name = intent.getStringExtra("name");
//        String user_dob = intent.getStringExtra("dateOfBirth");
//        String user_email = intent.getStringExtra("email");
//        String user_phoneNo = intent.getStringExtra("phoneNumber");
//        String user_password = intent.getStringExtra("password");
//        String user_UserName = intent.getStringExtra("userName");
//
//
//        name.setText(user_Full_name);
//        email.setText(user_email);
//        phoneNo.setText(user_phoneNo);
//        password.setText(user_password);
//        dob.setText(user_dob);
//        UserName.setText(user_UserName);
//
//    }
}