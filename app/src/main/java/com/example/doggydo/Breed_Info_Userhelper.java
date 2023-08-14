package com.example.doggydo;

public class Breed_Info_Userhelper {
    private String name,size;
    private int img;
    public Breed_Info_Userhelper(String name,String size,int img){

        this.name=name;
        this.size=size;
        this.img=img;
    }
    public String getName(){
        return name;
    }
    public String getSize(){
        return size;
    }
    public int getImg(){
        return img;
    }
}
