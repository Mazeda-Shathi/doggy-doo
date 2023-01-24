package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {
    TextInputLayout name, password, email, dob, phoneNo;
    Button backhome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Hooks
        name = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phoneNo_profile);
        password = findViewById(R.id.password_profile);
        dob = findViewById(R.id.dob_profile);
        backhome=findViewById(R.id.backHome);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(Profile.this,HomePage.class);
                startActivity(intent5);
            }
        });
        //show all data
        showAllUserData();

    }

    private void showAllUserData() {
        Intent intent = getIntent();

        String user_name = intent.getStringExtra("name");
        String user_dob = intent.getStringExtra("dateOfBirth");
        String user_email = intent.getStringExtra("email");
        String user_phoneNo = intent.getStringExtra("phoneNumber");
        String user_password = intent.getStringExtra("password");


        name.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);
        dob.getEditText().setText(user_dob);

    }
}