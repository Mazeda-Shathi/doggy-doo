package com.example.doggydo;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class myViewHolder extends RecyclerView.ViewHolder {
    CircleImageView ProfileImage;
    ImageView postImage,likeImage,commentImage,commentSend,dislike;
    public TextView userName;
    TextView timeAgo;
    TextView postDesc;
    TextView totalLike;
    TextView totalComment;
    TextView totalDislike;
    EditText input_comment;
    DatabaseReference likeRef,PostRef,dislikeRef;
    public static RecyclerView recyclerViewComment;
    public myViewHolder(@NonNull View itemView) {

        super(itemView);
        ProfileImage=itemView.findViewById(R.id.single_post_userImage);
        postDesc=itemView.findViewById(R.id.postDesc);
        userName=itemView.findViewById(R.id.postUserName);
        timeAgo=itemView.findViewById(R.id.postingTime);
        likeImage=itemView.findViewById(R.id.likeImage);
        dislike=itemView.findViewById(R.id.dislikeImage);
        totalLike=itemView.findViewById(R.id.totalLike);
        totalDislike=itemView.findViewById(R.id.totaldislike);
        totalComment=itemView.findViewById(R.id.Totalcomment);
        postImage=itemView.findViewById(R.id.PostImage);
        commentSend=itemView.findViewById(R.id.sendCommentId);
        input_comment=itemView.findViewById(R.id.writeCommentId);
        commentImage=itemView.findViewById(R.id.commentImage);
     //  recyclerViewComment=itemView.findViewById(R.id.commentRecyclerViewId);
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        likeRef = FirebaseDatabase.getInstance().getReference().child("Like");
        dislikeRef = FirebaseDatabase.getInstance().getReference().child("DisLike");

    }

    public void CountLike(String postKey, String uid, DatabaseReference likeRef) {
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalLikes=(int) snapshot.getChildrenCount();
                    totalLike.setText(totalLikes+"");

                }
                else {
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
                if(snapshot.child(uid).exists()){
                    likeImage.setColorFilter(Color.BLUE);


                }
                else{
                    likeImage.setColorFilter(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CountDislike(String postKey, String uid, DatabaseReference dislikeRef) {
        dislikeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalDisLikes=(int) snapshot.getChildrenCount();
                    totalDislike.setText(totalDisLikes+"");

                }
                else {
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
                if(snapshot.child(uid).exists()){
                    dislike.setColorFilter(Color.BLUE);


                }
                else{
                    dislike.setColorFilter(Color.BLACK);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void CountComment(String postKey, String uid, DatabaseReference commentRef) {
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
    public void likeSet(String postKey, String uid) {
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeRef.child(postKey).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            likeRef.child(postKey).child(uid).removeValue();
                           likeImage.setColorFilter(Color.BLACK);

                        }
                        else {
                            likeRef.child(postKey).child(uid).setValue("like");
                            likeImage.setColorFilter(Color.BLUE);

                            dislikeRef.child(postKey).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        dislikeRef.child(postKey).child(uid).removeValue();
                                        dislike.setColorFilter(Color.BLACK);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });


            }
        });
    }
    public void dislikeSet(String postKey, String uid) {
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikeRef.child(postKey).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            dislikeRef.child(postKey).child(uid).removeValue();
                            dislike.setColorFilter(Color.BLACK);

                        } else {
                            dislikeRef.child(postKey).child(uid).setValue("Dislike");
                           dislike.setColorFilter(Color.BLUE);

                            likeRef.child(postKey).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        likeRef.child(postKey).child(uid).removeValue();
                                       likeImage.setColorFilter(Color.BLACK);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


}
