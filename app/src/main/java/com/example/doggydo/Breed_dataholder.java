package com.example.doggydo;

import android.widget.Toast;

import com.airbnb.lottie.L;

import java.util.ArrayList;
import java.util.List;

public class Breed_dataholder {
    public static List<Breed_Info_Userhelper> getBreed_Info()
    {

       ArrayList<Breed_Info_Userhelper>Breed_Info=new ArrayList();
        Breed_Info_Userhelper b=new Breed_Info_Userhelper("Boxer","21 to 25 inches",R.drawable.boxer,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(b);
        Breed_Info_Userhelper german=new Breed_Info_Userhelper("German Shepherd","21 to 25 inches",R.drawable.german,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(german);
        Breed_Info_Userhelper golden=new Breed_Info_Userhelper("Golden Retriever","21 to 25 inches",R.drawable.golden_retriever,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(golden);
        Breed_Info_Userhelper golden2=new Breed_Info_Userhelper("Labrador","21 to 25 inches",R.drawable.labrador,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(golden2);
        Breed_Info_Userhelper golden3=new Breed_Info_Userhelper("frenchBulldog","21 to 25 inches",R.drawable.french_bulldog,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(golden3);
        Breed_Info_Userhelper golden4=new Breed_Info_Userhelper("shih_tzu","21 to 25 inches",R.drawable.shih_tzu,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(golden4);
        Breed_Info_Userhelper golden5=new Breed_Info_Userhelper("miniature","21 to 25 inches",R.drawable.miniature_schnauzer,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");
        Breed_Info.add(golden5);
        Breed_Info_Userhelper golden6=new Breed_Info_Userhelper("siberian","21 to 25 inches",R.drawable.siberian,"Goldens are outgoing, trustworthy, and eager-to-please family dogs, and relatively easy to train. They take a joyous and playful approach to life and maintain this puppyish behavior into adulthood.");

        Breed_Info.add(golden6);
        return Breed_Info;
    }
}
