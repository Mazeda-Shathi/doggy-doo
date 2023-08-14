package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doggydo.Utills.Posts;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    CardView classifyCard,AllBreedCard,BlogCard,historyCard;
 String drawerUserName,proImgDrawerUl;
 CircleImageView drawerProfile;
 TextView drawerUserTv;
   FirebaseAuth mAuth;
   FirebaseUser mUser;
 DatabaseReference mRef;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View view=navigationView.inflateHeaderView(R.layout.header);
//       drawerUserTv=view.findViewById(R.id.drawerUsername);
//       drawerProfile=view.findViewById(R.id.proImage_drawer);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("UserDetails");
        classifyCard=findViewById(R.id.classifYId);
        BlogCard=findViewById(R.id.blogId);
        AllBreedCard=findViewById(R.id.allBreedId);
        menu = navigationView.getMenu();
        historyCard=findViewById(R.id.historyId);




//navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open_drawer,R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        classifyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomePage.this,classify.class);
                startActivity(in);
            }
        });
        historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomePage.this,History.class);
                startActivity(in);
            }
        });
        AllBreedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomePage.this,All_Breed.class);
                startActivity(in);

            }
        });
        BlogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(HomePage.this,Blog.class);
                startActivity(in);
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        switch (menuitem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_classify:
                Intent in=new Intent(HomePage.this,classify.class);
                startActivity(in);
                break;

            case R.id.nav_petCare:
                Intent intent2=new Intent(HomePage.this,PetCare.class);
                startActivity(intent2);
                break;
            case R.id.nav_aboutUs:
                Intent intent3=new Intent(HomePage.this,AboutUs.class);
                startActivity(intent3);
                break;
            case R.id.nav_logOut:
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("remember","false");
                editor.apply();
               finish();
                Intent intent4=new Intent(HomePage.this,Login.class);
                startActivity(intent4);
                break;
            case R.id.nav_prfoile:
                Intent intent5=new Intent(HomePage.this,Profile.class);
                startActivity(intent5);
                break;
            case R.id.nav_Blog:
                Intent intent6=new Intent(HomePage.this,Blog.class);
                startActivity(intent6);
                break;
            case R.id.nav_rateUS:
                Intent intent7=new Intent(HomePage.this,RateUs.class);
                startActivity(intent7);
                break;

            case R.id.nav_location:
                Intent intent8=new Intent(HomePage.this,Location.class);
                startActivity(intent8);
                break;




        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}