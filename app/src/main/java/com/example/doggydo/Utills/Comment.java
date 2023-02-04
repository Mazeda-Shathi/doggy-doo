package com.example.doggydo.Utills;

public class Comment {


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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String comments;
    public Comment(){

    }

    public Comment(String userName, String UserProfileImageUrl, String comments) {
       this.userName=userName;
       this.UserProfileImageUrl=UserProfileImageUrl;
       this.comments=comments;

    }



}
