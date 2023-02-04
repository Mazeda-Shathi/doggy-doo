package com.example.doggydo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class myViewHolder extends RecyclerView.ViewHolder {
    CircleImageView ProfileImage;
    ImageView postImage,likeImage,commentImage;
    TextView userName,timeAgo,postDesc,totalLike,totalComment;
    public myViewHolder(@NonNull View itemView) {

        super(itemView);

        ProfileImage=itemView.findViewById(R.id.single_post_userImage);
        postDesc=itemView.findViewById(R.id.postDesc);
        userName=itemView.findViewById(R.id.postUserName);
        timeAgo=itemView.findViewById(R.id.postingTime);
        likeImage=itemView.findViewById(R.id.likeImage);
        commentImage=itemView.findViewById(R.id.commentImage);
       totalLike=itemView.findViewById(R.id.totalLike);
        totalComment=itemView.findViewById(R.id.Totalcomment);
       postImage=itemView.findViewById(R.id.PostImage);


    }
}
