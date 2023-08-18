package com.example.doggydo;

public class Breed_Info_Userhelper {
    private String name,size,description;
    private int img;
    public Breed_Info_Userhelper(String name,String size,int img,String description){

        this.name=name;
        this.size=size;
        this.img=img;
        this.description=description;
    }
    public String getName(){
        return name;
    }
    public String getSize(){
        return size;
    }
    public String getDescription(){
        return description;
    }
    public int getImg(){
        return img;
    }
}
