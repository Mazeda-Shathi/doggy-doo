package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText inputEmail,inputpassword;
 TextView gotoRegister;
 FirebaseAuth mAuth;
 CheckBox remindMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gotoRegister=findViewById(R.id.btngotoRegister);

        inputEmail=findViewById(R.id.log_Email);
        inputpassword=findViewById(R.id.loginPass);
        remindMe=findViewById(R.id.remindId);
        mAuth=FirebaseAuth.getInstance();
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            Intent in=new Intent(Login.this,HomePage.class);
            startActivity(in);
        }
        else if(checkbox.equals("false"))
        {
            Toast.makeText(this, "Please Log in", Toast.LENGTH_SHORT).show();
        }


        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });
        remindMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Login.this, "checked", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Login.this, "Unchecked", Toast.LENGTH_SHORT).show();

                }

            }
        });


        
    }
    private Boolean validateEmail(){
        String val=inputEmail.getText().toString();
        String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            inputEmail.setError("Field cannot be empty");
            return false;

        }
        else if(!val.matches(emailPattern)){
            inputEmail.setError("Invalid email address");
            return false;
        }

        else{
            inputEmail.setError(null);
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
        if(!validateEmail() | !validatePassword()){
            return;
        }

        String em=inputEmail.getText().toString();
        String pa=inputpassword.getText().toString();


        mAuth.signInWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Login.this,"Successfully login",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,HomePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                   // Toast.makeText(Login.this,task.getException().toString(),Toast.LENGTH_SHORT);
                    Toast.makeText(Login.this,"Please Enter correct password",Toast.LENGTH_SHORT);
                }
            }
        });

    }



}