package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggydo.Utills.Comment;
import com.example.doggydo.Utills.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class details_blog extends AppCompatActivity {

    ImageView postImage, likeImage, commentImage, commentSend, dislike;
    CircleImageView ProfileImage;
    TextView userName, timeAgo, postDesc, totalLike, totalComment, totalDislike, writeComment;
    FirebaseAuth mAuth;
    DatabaseReference mRef, PostRef, likeRef, commentRef, dislikeRef, sendCommentRef;
    FirebaseUser mUser;
    String profileImageurl, uname, postKey, uId;
    ProgressDialog mloadingBar;
    RecyclerView commentRecyclerView;
    FirebaseStorage storage;
    FirebaseRecyclerAdapter<Posts, myViewHolder> adapter;
    FirebaseRecyclerOptions<Posts> options;
    Comment comment;


    FirebaseRecyclerAdapter<Comment, CommentViewHolder> Commentadapter;
    FirebaseRecyclerOptions<Comment> CommentOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_blog);


        ProfileImage = findViewById(R.id.details_post_userImage);
        postDesc = findViewById(R.id.detailsPostDesc);
        userName = findViewById(R.id.detailspostUserName);
        timeAgo = findViewById(R.id.detailsPostingTime);
        likeImage = findViewById(R.id.detailsLikeImage);
        dislike = findViewById(R.id.detailsPost_dislikeImage);
        totalLike = findViewById(R.id.detailsPost_totalLike);
        totalDislike = findViewById(R.id.detailsPost_totaldislike);
        totalComment = findViewById(R.id.detailsPost_Totalcomment);
        postImage = findViewById(R.id.detailsPostImage);
        writeComment = findViewById(R.id.writeCommentId);
        commentSend = findViewById(R.id.sendCommentId);

        commentRecyclerView = findViewById(R.id.commentRecyclerViewId);


        //firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        likeRef = FirebaseDatabase.getInstance().getReference().child("Like");
        dislikeRef = FirebaseDatabase.getInstance().getReference().child("DisLike");
        commentRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        storage = FirebaseStorage.getInstance();
        uId = mUser.getUid();


        //each data about post which are fetch from blog.java
        Intent intent = getIntent();
        postKey = intent.getExtras().getString("postKey");
        final String userId = intent.getExtras().getString("user Id");
        final String userN = intent.getExtras().getString("user name");
        final String post_Desc = intent.getExtras().getString("post desc");
        final String profile_image = intent.getExtras().getString("post user profile");
        final String time_ago = intent.getExtras().getString("post date");



        final String post_image = intent.getExtras().getString("post image url");
        //set post data
        userName.setText(userN);
        postDesc.setText(post_Desc);
        timeAgo.setText(time_ago);
        Picasso.get().load(profile_image).into(ProfileImage);
        Picasso.get().load(post_image).into(postImage);


        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcomment();

            }
        });


        LoadComment(postKey);
        countLike(postKey, uId, likeRef);
        countDislike(postKey, uId, dislikeRef);
        countComment(postKey,uId,commentRef);


    }



    public void countComment(String postKey, String uid, DatabaseReference commentRef) {
        commentRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalCmnt=(int) snapshot.getChildrenCount();
                    totalComment.setText(totalCmnt+"");

                }
                else {
                    totalComment.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    protected void onStart() {
        //   Toast.makeText(this, ""+mRef.child(mUser.getUid()).child("userName").get().toString(), Toast.LENGTH_SHORT).show();
        super.onStart();
        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileImageurl = snapshot.child("Profile_Image").getValue(String.class);
                uname = snapshot.child("userName").getValue(String.class);
                Toast.makeText(details_blog.this, "" + uname, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void countLike(String postKey, String uId, DatabaseReference likeRef) {
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalLikes = (int) snapshot.getChildrenCount();
                    totalLike.setText(totalLikes + "");

                } else {
                    totalLike.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uId).exists()) {
                    likeImage.setColorFilter(Color.BLUE);


                } else {
                    likeImage.setColorFilter(Color.BLACK);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void countDislike(String postKey, String uId, DatabaseReference dislikeRef) {
        dislikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalDisLikes = (int) snapshot.getChildrenCount();
                    totalDislike.setText(totalDisLikes + "");

                } else {
                    totalDislike.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dislikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uId).exists()) {
                    dislike.setColorFilter(Color.BLUE);


                } else {
                    dislike.setColorFilter(Color.BLACK);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addcomment() {
        sendCommentRef = commentRef.child(postKey).push();
        String comment_content = writeComment.getText().toString();
        if(comment_content.isEmpty())
        {
            Toast.makeText(this, "please write something", Toast.LENGTH_SHORT).show();
        }
        else{
            comment = new Comment(uname, profileImageurl, comment_content);
        }

        sendCommentRef.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(details_blog.this, "Comment Added", Toast.LENGTH_SHORT).show();
                writeComment.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(details_blog.this, "fail to add comment" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void LoadComment(String postKey) {


        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentOptions = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(commentRef.child(postKey), Comment.class).build();
        Commentadapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(CommentOptions) {
            @Override
            protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull Comment model) {


                holder.commentUserName.setText(model.getUserName());
                holder.comment.setText(model.getComments());
                if (model.getUserProfileImageUrl() == null) {
                    Picasso.get().load(R.drawable.dp).into(holder.commentUserProfile);
                } else {
                    Picasso.get().load(model.getUserProfileImageUrl()).into(holder.commentUserProfile);
                }


            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comment, parent, false);
                return new CommentViewHolder(view);

            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

        Commentadapter.startListening();
        commentRecyclerView.setAdapter(Commentadapter);
    }
}
