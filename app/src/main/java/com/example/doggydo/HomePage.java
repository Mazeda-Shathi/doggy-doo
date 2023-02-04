package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.doggydo.Utills.Posts;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //  textView=findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);

        //hide or show menuitem
        menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logOut).setVisible(false);
//        menu.findItem(R.id.nav_prfoile).setVisible(false);

//navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open_drawer,R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);



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
                Intent intent=new Intent(HomePage.this,classify.class);
                startActivity(intent);
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
                FirebaseAuth.getInstance().signOut();
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


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}