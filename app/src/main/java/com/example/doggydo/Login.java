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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gotoRegister=findViewById(R.id.btngotoRegister);

        inputEmail=findViewById(R.id.log_Email);
        inputpassword=findViewById(R.id.loginPass);
        mAuth=FirebaseAuth.getInstance();

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
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
                    Toast.makeText(Login.this,"Login is successfull",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this,Profile.class);
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

//    private void isUser() {
//
//            final String userEnteredname = inputName.getText().toString().trim();
//            final String userEnteredPassword = inputpassword.getText().toString().trim();
//
//            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
//            Query checkUser = reference.orderByChild("name").equalTo(userEnteredname);
//            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    inputName.setError(null);
//
//
//                    if (snapshot.exists()) {
//                        inputName.setError(null);
//
//                        String passwordFromDB = snapshot.child(userEnteredname).child("password").getValue(String.class);
//
//                        if (passwordFromDB.equals(userEnteredPassword)) {
//                            String nameFromDB = snapshot.child(userEnteredname).child("name").getValue(String.class);
//                            String dateOfBirthDB = snapshot.child(userEnteredname).child("dateOfBirth").getValue(String.class);
//                            String emailFromDB = snapshot.child(userEnteredname).child("email").getValue(String.class);
//                            String phoneFromDB = snapshot.child(userEnteredname).child("phoneNumber").getValue(String.class);
//
//                            Intent intent = new Intent(getApplicationContext(), Profile.class);
//
//
//                            intent.putExtra("name", nameFromDB);
//                            intent.putExtra("email", emailFromDB);
//                            intent.putExtra("phoneNumber", phoneFromDB);
//                            intent.putExtra("dateOfBirth", dateOfBirthDB);
//                            intent.putExtra("password", passwordFromDB);
//                            Toast.makeText(getApplicationContext(),"Successfully login",Toast.LENGTH_SHORT).show();
//
//                            startActivity(intent);
//                        } else {
//                            inputpassword.setError("Wrong Password");
//                            inputpassword.requestFocus();
//                        }
//
//                    } else {
//                        inputName.setError("Wrong UserName");
//                        inputName.requestFocus();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }

}