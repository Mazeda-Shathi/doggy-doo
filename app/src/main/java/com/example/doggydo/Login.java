package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText inputName,inputpassword;
 TextView gotoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gotoRegister=findViewById(R.id.btngotoRegister);

        inputName=findViewById(R.id.loginName);
        inputpassword=findViewById(R.id.loginPass);

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });



    }
    private Boolean validateName(){
        String val=inputName.getText().toString();
        if(val.isEmpty()){
            inputName.setError("Please enter your name");
            return false;
        }
        else{
            inputName.setError(null);
            return true;

        }
    }
    private Boolean validatePassword(){
        String val=inputpassword.getText().toString();
        if(val.isEmpty()){
            inputpassword.setError("Field cannot be empty");
            return false;
        }

        else{
            inputpassword.setError(null);
            return true;

        }
    }
    public void loginUser(View view){
        //validate login info
        if(!validateName() | !validatePassword()){
            return;
        }
        else{
            isUser();
        }
    }

    private void isUser() {

            final String userEnteredname = inputName.getText().toString().trim();
            final String userEnteredPassword = inputpassword.getText().toString().trim();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
            Query checkUser = reference.orderByChild("name").equalTo(userEnteredname);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    inputName.setError(null);


                    if (snapshot.exists()) {
                        inputName.setError(null);

                        String passwordFromDB = snapshot.child(userEnteredname).child("password").getValue(String.class);
                        Toast.makeText(getApplicationContext(),"password is"+passwordFromDB,Toast.LENGTH_LONG).show();
                        if (passwordFromDB.equals(userEnteredPassword)) {
                            String nameFromDB = snapshot.child(userEnteredname).child("name").getValue(String.class);
                            String dateOfBirthDB = snapshot.child(userEnteredname).child("dateOfBirth").getValue(String.class);
                            String emailFromDB = snapshot.child(userEnteredname).child("email").getValue(String.class);
                            String phoneFromDB = snapshot.child(userEnteredname).child("phoneNumber").getValue(String.class);

                            Intent intent = new Intent(getApplicationContext(), classify.class);

                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("email", nameFromDB);
                            intent.putExtra("phoneNumber", nameFromDB);
                            intent.putExtra("dateOfBirth", nameFromDB);
                            intent.putExtra("password", nameFromDB);

                            startActivity(intent);
                        } else {
                            inputpassword.setError("Wrong Password");
                            inputpassword.requestFocus();
                        }

                    } else {
                        inputName.setError("Wrong UserName");
                        inputName.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

}