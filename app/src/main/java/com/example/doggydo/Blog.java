package com.example.doggydo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doggydo.Utills.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blog extends AppCompatActivity {

    ImageView addImagePost, sendImagePost;
    FirebaseAuth mAuth;
    DatabaseReference mRef, PostRef;
    FirebaseUser mUser;
    ProgressDialog mloadingBar;
    FirebaseStorage storage;
    StorageReference postImageRef;
    Uri imguri = null;
    CircleImageView proImage;
    String profileImageurl, uname;
    EditText inputPostDescription;


    //  CircleImageView profileImage,postImage;

    int REQUEST_CODE = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        addImagePost = findViewById(R.id.addImagePost);
        sendImagePost = findViewById(R.id.sendImagePost);
        inputPostDescription = findViewById(R.id.inputAddPost);
        mloadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        storage = FirebaseStorage.getInstance();
        postImageRef = storage.getReference().child("PostImage");

        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Blog.this, "Clicked", Toast.LENGTH_SHORT).show();
                AddPost();
            }
        });
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
//        recyclerView=findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        LoadPost();
    }

//    private void LoadPost() {
//        options=new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostRef,Posts.class).build();
//        adapter=new FirebaseRecyclerAdapter<Posts, myViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
//                holder.postDesc.setText(model.getPostDesc());
//                holder.timeAgo.setText(model.getDatePost());
//                holder.userName.setText(model.getUserName());
//                Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
//                Picasso.get().load(model.getUserProfileImageUrl()).into(holder.ProfileImage);
//
//            }
//
//
//            @NonNull
//            @Override
//            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post,parent,false);
//
//                return new myViewHolder(view);
//            }
//        }  ;
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
//    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imguri = data.getData();
            addImagePost.setImageURI(imguri);
        }
    }

    protected void onStart() {
        super.onStart();
        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileImageurl = snapshot.child("Profile_Image").getValue(String.class);
                uname = snapshot.child("userName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    private void AddPost() {
        String postDesc = inputPostDescription.getText().toString();
        if (postDesc.isEmpty()) {
            inputPostDescription.setError("Please write something in  post description");

        } else {

            //Toast.makeText(this, imguri.toString(), Toast.LENGTH_SHORT).show();

            mloadingBar.setTitle("Adding Post");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();


            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
            String strDate = formatter.format(date);

            HashMap hashMap = new HashMap();
            hashMap.put("datePost", strDate);
            hashMap.put("postDesc", postDesc);
            hashMap.put("postImageUrl", "");
            hashMap.put("UserProfileImageUrl", profileImageurl);
            hashMap.put("userName", uname);

            //  Toast.makeText(Profile.this,"name "+name,Toast.LENGTH_SHORT).show();

            PostRef.child(mUser.getUid() + strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        mloadingBar.dismiss();
                        Toast.makeText(Blog.this, "post added", Toast.LENGTH_SHORT).show();
                        inputPostDescription.setText("");
                    } else {
                        mloadingBar.dismiss();
                        Toast.makeText(Blog.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
//
            postImageRef.child(mUser.getUid() + strDate).putFile(imguri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    postImageRef.child(mUser.getUid() + strDate).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            HashMap hashMap = new HashMap();
                            hashMap.put("datePost", strDate);
                            hashMap.put("postDesc", postDesc);
                            hashMap.put("postImageUrl", uri.toString());
                            hashMap.put("UserProfileImageUrl", profileImageurl);
                            hashMap.put("userName", uname);

                              Toast.makeText(Blog.this,"postImageUrl "+uri.toString(),Toast.LENGTH_SHORT).show();

                            PostRef.child(mUser.getUid() + strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        mloadingBar.dismiss();
                                        Toast.makeText(Blog.this, "post added", Toast.LENGTH_SHORT).show();
//                                            addImagePost.setImageResource(R.id.addImagePost);
//                                            inputPostDescription.setText("");
                                    } else {
                                        mloadingBar.dismiss();
                                        Toast.makeText(Blog.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                        }

                    });


                }
            });
        }
    }
}
