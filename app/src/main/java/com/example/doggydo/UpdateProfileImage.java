package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class UpdateProfileImage extends AppCompatActivity {
    private static final int REQUEST_CODE =10 ;
    CircleImageView Proimg;
    FirebaseStorage storage;
    StorageReference storageRef;
    Button btnUpdate,btnSkip;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;
    Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_image);
        Proimg=findViewById(R.id.profile_image);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnSkip=findViewById(R.id.btnSkip);

        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference().child("UserDetails");

        storage= FirebaseStorage.getInstance();
        storageRef=storage.getReference().child("Profile_Image");
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
                String uId= FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                                        Toast.makeText(UpdateProfileImage.this,"img added",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(UpdateProfileImage.this,Profile.class);
                                        startActivity(intent);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(UpdateProfileImage.this,"img failed",Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });


                    }
                });
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(UpdateProfileImage.this,HomePage.class);
                startActivity(in);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            imguri=data.getData();
            Proimg.setImageURI(imguri);
        }
    }
    protected void onStart() {
        super.onStart();

        {
            String uId=mUser.getUid();
            // Toast.makeText(Profile.this,"id "+uId,Toast.LENGTH_SHORT).show();

            mRef.child(uId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        String PimgUrl=snapshot.child("Profile_Image").getValue(String.class);
                        if(PimgUrl== null)
                        {
                            Proimg.setImageResource(R.drawable.user_profile);
                        }
                        else {
                            Picasso.get().load(PimgUrl).into(Proimg);
                        }



                        //  Toast.makeText(Profile.this,PimgUrl,Toast.LENGTH_SHORT).show();



                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UpdateProfileImage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

}