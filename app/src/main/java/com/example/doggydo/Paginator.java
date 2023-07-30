package com.example.doggydo;

import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;

public class Paginator {
   public static final int Total_items=Breed_dataholder.getBreed_Info().size();
   public static final  int Items_per_page=3;

   public Paginator(){

   }

public int getTotalPage(){
      int remainingItems=Total_items % Items_per_page;
      if(remainingItems>0)
      {
         return  (Total_items/Items_per_page);

      }

    return (Total_items/Items_per_page)-1;
}

//current page
   public  ArrayList<Breed_Info_Userhelper>getCurrentBreedInfo(int currentPage){
      int startItem=currentPage*Items_per_page;
      int lastItem=startItem+Items_per_page;

      ArrayList<Breed_Info_Userhelper>currentBreedInfo= new ArrayList<>();
      try {
         for (int i=0;i<Breed_dataholder.getBreed_Info().size();i++){

            //add currnet page data
            if(i>=startItem && i<lastItem){
               currentBreedInfo.add(Breed_dataholder.getBreed_Info().get(i));


            }
         }
         return currentBreedInfo;

      }
      catch (Exception e)

      {
         e.printStackTrace();
         return null;
      }


   }

}
