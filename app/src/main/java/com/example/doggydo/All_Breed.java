package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class All_Breed extends AppCompatActivity {

    ListView gv;

    Button next,previous;
    Paginator p=new Paginator();
    private  int totalPages=p.getTotalPage();
    ArrayList<Breed_Info_Userhelper> Breed_info = new ArrayList<>();
    private int currentpage=0;
    Breed_CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_breed);

        gv=findViewById(R.id.Breed_gridview_id);
        next=findViewById(R.id.id_next);
        previous=findViewById(R.id.id_previous);
        previous.setEnabled(false);
        this.binddata(currentpage);
        ListView listView = findViewById(R.id.Breed_gridview_id);
//       Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//        Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//        Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//        Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//        Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//        Breed_info.add(new Breed_Info_Userhelper("boxer","21",R.drawable.boxer));
//
////        Breed_info.add(new get_("boxer","21",R.drawable.boxer));
//        adapter = new Breed_CustomAdapter(this,Breed_info);
//        listView.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpage+=1;
                binddata(currentpage);
                toggleButton();

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpage-=1;
                if(currentpage>=0) {
                    binddata(currentpage);
                    toggleButton();
                }
            }
        });

    }

    private void binddata(int currentpage) {
        adapter=new Breed_CustomAdapter(this,p.getCurrentBreedInfo(currentpage));
        gv.setAdapter(adapter);
    }

    private void toggleButton() {
        if(totalPages<=1){
            previous.setEnabled(false);
            next.setEnabled(false);
        }
        else if(currentpage==totalPages)
        {
            previous.setEnabled(true);
            next.setEnabled(false);

        }
        else if(currentpage==0){
            previous.setEnabled(false);
            next.setEnabled(true);
        }
        else if(currentpage>=1 && currentpage<=totalPages){
            previous.setEnabled(true);
            next.setEnabled(true);
        }

    }
}