package com.example.doggydo.Utills;

public class Posts {
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
    public  Posts(String postDesc,String userName,String UserProfileImageUrl,String postImageUrl,String datePost){
 this.datePost=datePost;
 this.userName=userName;
 this.postImageUrl=postImageUrl;
 this.postDesc=postDesc;
this.UserProfileImageUrl=UserProfileImageUrl;
    }
}
