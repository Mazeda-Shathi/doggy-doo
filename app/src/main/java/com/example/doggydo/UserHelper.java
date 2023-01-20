package com.example.doggydo;


public class UserHelper {

    String name,dateaOfBirth,phoneNumber,email,password;

    public UserHelper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserHelper(String name, String email, String phoneNumber, String dateaOfBirth, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateaOfBirth = dateaOfBirth;
        this.password = password;

    }


}
