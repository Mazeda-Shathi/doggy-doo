package com.example.doggydo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder  extends RecyclerView.ViewHolder {

    CircleImageView commentUserProfie;
    TextView commentUserName,comment;
    public CommentViewHolder(@NonNull View itemView) {

        super(itemView);
        comment=itemView.findViewById(R.id.userCommentId);
        commentUserName=itemView.findViewById(R.id.CommentUserNameId);
        commentUserProfie=itemView.findViewById(R.id.CommentUserImageId);

    }
}
