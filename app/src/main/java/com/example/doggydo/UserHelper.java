package com.example.doggydo;


import de.hdodenhof.circleimageview.CircleImageView;

public class UserHelper {


    String name,dateaOfBirth,phoneNumber,userName,email,password,Proimg;

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

    public UserHelper(String name, String phoneNumber, String dateaOfBirth,String userName,String email,String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dateaOfBirth = dateaOfBirth;
        this.userName= userName;
        this.email=email;
        this.password=password;


    }


}
