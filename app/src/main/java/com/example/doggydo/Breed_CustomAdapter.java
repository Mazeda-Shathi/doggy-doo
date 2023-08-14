package com.example.doggydo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Breed_CustomAdapter extends BaseAdapter {


    Context c;
    ArrayList<Breed_Info_Userhelper>Breed_Info;

    @Override
    public int getCount() {
        return Breed_Info.size();
    }

    @Override
    public Object getItem(int position) {
        return Breed_Info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v==null)
        {
            v= LayoutInflater.from(c).inflate(R.layout.model_breed,parent,false);
        }
        TextView name=v.findViewById(R.id.id_model_breed_name);
        TextView size=v.findViewById(R.id.id_model_breed_size);
        ImageView breed_img=v.findViewById(R.id.id_model_breed_img);


        //bind bigdata
        Breed_Info_Userhelper b=(Breed_Info_Userhelper) getItem(position);

        size.setText(Breed_Info.get(position).getSize());
        name.setText(Breed_Info.get(position).getName());
        breed_img.setImageResource(Breed_Info.get(position).getImg());
//        breed_img.setImageResource(b.getImg());

        return v;
    }

    public Breed_CustomAdapter(Context c,ArrayList<Breed_Info_Userhelper> Breed_Info) {

        this.c=c;
        this.Breed_Info = Breed_Info;
    }
}
