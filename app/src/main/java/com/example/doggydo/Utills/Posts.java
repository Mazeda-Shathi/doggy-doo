package com.example.doggydo.Utills;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doggydo.myViewHolder;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class Posts extends  RecyclerView.Adapter<myViewHolder>{
    //adapter
    Context c;
    ArrayList<String> spacecrafts;

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    private  String datePost;

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    private String postDesc;

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    private String postImageUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public String getUserProfileImageUrl() {
        return UserProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        UserProfileImageUrl = userProfileImageUrl;
    }

    private String UserProfileImageUrl;
    public  Posts(){


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.userName.setText(spacecrafts.get(position));

    }

    @Override
    public int getItemCount() {
        return spacecrafts.size();
    }

    public  Posts(String postDesc,String userName,String UserProfileImageUrl,String postImageUrl,String datePost,Context c,ArrayList<String>spacecrafts){
 this.datePost=datePost;
 this.userName=userName;
 this.postImageUrl=postImageUrl;
 this.postDesc=postDesc;
this.UserProfileImageUrl=UserProfileImageUrl;
this.c=c;
this.spacecrafts=spacecrafts;
    }
}
