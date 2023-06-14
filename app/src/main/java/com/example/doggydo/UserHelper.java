package com.example.doggydo;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserHelper {


    String name;
    String dateaOfBirth;
    String phoneNumber;
    String userName;
    String email;
    String password;
    String Proimg;


    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    String dis;

    public String getTha() {
        return tha;
    }

    public void setTha(String tha) {
        this.tha = tha;
    }

    String tha;

    public UserHelper(CircleImageView proimg) {


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName= userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateaOfBirth() {
        return dateaOfBirth;
    }

    public void setDateaOfBirth(String dateaOfBirth) {
        this.dateaOfBirth = dateaOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserHelper(String name, String phoneNumber, String dateaOfBirth, String userName, String email, String password, String dis, String tha) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateaOfBirth = dateaOfBirth;
        this.userName= userName;
        this.email=email;
        this.password=password;
        this.tha=tha;
        this.dis=dis;


    }


}
