package com.example.doggydo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggydo.Utills.Comment;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class Blog extends AppCompatActivity {

    ImageView addImagePost, sendImagePost,commentSend,commentImage;
    FirebaseAuth mAuth;
    DatabaseReference mRef, PostRef,likeRef,commentRef,dislikeRef,ratingRef;
    FirebaseUser mUser;
    ProgressDialog mloadingBar;
    FirebaseStorage storage;
    StorageReference postImageRef;
    Uri imguri = null;

    String profileImageurl, uname;
    String key = null, startKey = null;
    EditText inputPostDescription;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Posts,myViewHolder> adapter;
    FirebaseRecyclerOptions<Posts> options;
    FirebaseRecyclerAdapter<Comment,CommentViewHolder> Commentadapter;
    FirebaseRecyclerOptions<Comment> CommentOptions;
    Button prevBtn,NextBtn;



    int REQUEST_CODE = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        addImagePost = findViewById(R.id.addImagePost);
        sendImagePost = findViewById(R.id.sendImagePost);
        inputPostDescription = findViewById(R.id.inputAddPost);
//        commentImage=findViewById(R.id.commentImage);
        mloadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        likeRef = FirebaseDatabase.getInstance().getReference().child("Like");
        dislikeRef = FirebaseDatabase.getInstance().getReference().child("DisLike");
        commentRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        storage = FirebaseStorage.getInstance();
        postImageRef = storage.getReference().child("PostImage");


        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        prevBtn=findViewById(R.id.previousBtnId);
        NextBtn=findViewById(R.id.NextBtnId);

       LoadPost();

    }





    private void LoadPost() {


//        Query query = PostRef.orderByChild("datePost");
         options=new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostRef,Posts.class).build();
         adapter=new FirebaseRecyclerAdapter<Posts, myViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Posts model) {
                String postKey=getRef(position).getKey();
                holder.postDesc.setText(model.getPostDesc());
                String timeAgo=calculateTime(model.getDatePost());
                holder.timeAgo.setText(timeAgo);
                holder.userName.setText(model.getUserName());
                holder.CountLike(postKey,mUser.getUid(),likeRef);
                holder.CountDislike(postKey,mUser.getUid(),dislikeRef);
                holder.CountComment(postKey,mUser.getUid(),commentRef);
                holder.likeSet(postKey,mUser.getUid());
                holder.dislikeSet(postKey,mUser.getUid());
                if(model.getPostImageUrl()==null){
                    holder.postImage.setVisibility(View.GONE);
                }

              else  if(model.getPostImageUrl() !="") {
                    holder.postImage.setVisibility(View.VISIBLE);
                    Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
                }
                if(model.getUserProfileImageUrl()==null)
                {
                    Picasso.get().load(R.drawable.dp).into(holder.ProfileImage);
                }
                else
                {
                    Picasso.get().load(model.getUserProfileImageUrl()).into(holder.ProfileImage);
                }

                holder.commentImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in=new Intent(Blog.this,details_blog.class);
                        in.putExtra("postKey",  postKey);
                        if(model.getUserProfileImageUrl()!=null)
                        {
                            in.putExtra("post user profile",model.getUserProfileImageUrl());
                        }
                        else {
                            in.putExtra("post user profile",R.drawable.dp);
                        }

                        in.putExtra("post date",timeAgo);
                        in.putExtra("post image url",model.getPostImageUrl());
                        in.putExtra("user name",model.getUserName());
                        in.putExtra("post desc",model.getPostDesc());
                        in.putExtra("user Id",mUser.getUid());
                        startActivity(in);
                    }
                });

            }


            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.only_post,parent,false);

                return new myViewHolder(view);
            }

         }  ;
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



    private String calculateTime(String datePost) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(datePost).getTime();
            long now = System.currentTimeMillis()-(6*3600*1000);
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

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
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String strDate = formatter.format(date);


            HashMap hashMap = new HashMap();
            hashMap.put("datePost", strDate);
            hashMap.put("postDesc", postDesc);
            hashMap.put("UserProfileImageUrl", profileImageurl);
            hashMap.put("userName", uname);
            if(imguri!=null)
            {
                hashMap.put("postImageUrl", "");
            }

            //  Toast.makeText(Profile.this,"name "+name,Toast.LENGTH_SHORT).show();

            PostRef.child(mUser.getUid() + strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
//                        mloadingBar.dismiss();
//                        Toast.makeText(Blog.this, "post added", Toast.LENGTH_SHORT).show();
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
                            hashMap.put("UserProfileImageUrl", profileImageurl);
                            hashMap.put("userName", uname);
                            if(uri!=null)
                            {
                                hashMap.put("postImageUrl", uri.toString());
                            }
                           //  Toast.makeText(Blog.this,"postImageUrl "+uri.toString(),Toast.LENGTH_SHORT).show();

                            PostRef.child(mUser.getUid() + strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        mloadingBar.dismiss();
                                        Toast.makeText(Blog.this, "post added successfully", Toast.LENGTH_SHORT).show();

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
