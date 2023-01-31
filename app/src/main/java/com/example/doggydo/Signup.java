package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    EditText datepicker,inputName,inputEmail,inputPhoneNumber,inputDateOfBirth,inputpassword,inputUsername;
    int year,month,day;
    Button btnregSubmit;
    TextView  gotolog;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    DatePickerDialog.OnDateSetListener setListener;
    FirebaseAuth mAuth;
    CircleImageView Proimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //sign up section
        inputName=findViewById(R.id.regName);
        inputPhoneNumber=findViewById(R.id.RegPNum);
        inputDateOfBirth=findViewById(R.id.regdob);
        inputpassword=findViewById(R.id.RegPass);
        inputEmail=findViewById(R.id.RegEmail);
        inputUsername=findViewById(R.id.Reg_userName);

        btnregSubmit=findViewById(R.id.btnRegSub);
        gotolog=findViewById(R.id.btngotoLog);
        mAuth=FirebaseAuth.getInstance();
         //for date
        datepicker=findViewById(R.id.regdob);
        final Calendar calendar=Calendar.getInstance();
       final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

      //  for date pf birth
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for date of birth
                DatePickerDialog datePickerDialog= new DatePickerDialog(Signup.this, android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day) ;

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     datePickerDialog.show();


            }
        });
    setListener=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month=month+1;
            String date=day+"/"+month+"/"+year;
            datepicker.setText(date);
        }
    };

    //go to login page
        gotolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        btnregSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!validateName() | !validateEmail() | !validatePhoneNumber() | !validatePassword() | !validatedob() | !validateUsername())

                    {
                        return;

                    }
                    String em=inputEmail.getText().toString();
                    String pa=inputpassword.getText().toString();

                    mAuth.createUserWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this,"Registration is successfull",Toast.LENGTH_SHORT).show();
                                saveDatabase();
                                Intent intent=new Intent(Signup.this,Profile.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Signup.this,"Registration is Failed",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }

        });

    }



    //for validate data
    private Boolean validateName(){
        String val=inputName.getText().toString();

        if(val.isEmpty()){
            inputName.setError("Field cannot be empty");
            return false;

        }
        else{
            inputName.setError(null);

            return true;

        }
    }
    private Boolean validatedob(){
        String val=inputDateOfBirth.getText().toString();
        if(val.isEmpty()){
            inputDateOfBirth.setError("Field cannot be empty");
            return false;

        }
        else{
            inputDateOfBirth.setError(null);

            return true;

        }
    }
    private Boolean validatePhoneNumber(){
        String val=inputPhoneNumber.getText().toString();
        String phonNumberpattern="[0][1][^012]{1}[0-9]{8}";
        if(val.isEmpty()){
            inputPhoneNumber.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(phonNumberpattern)){
            inputPhoneNumber.setError("Invalid Phone Number");
            return false;
        }
        else{
            inputPhoneNumber.setError(null);
            return true;

        }
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
        String passwordVal="^(?=.*[0-9])(?=.*[a-zA-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        if(val.isEmpty()){
          inputpassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passwordVal)){
            inputpassword.setError("Password Has To Be At Least 6 Characters Long ,contain atleast 1 digit && 1 special character");
            return false;
        }
        else{
            inputpassword.setError(null);
            return true;

        }
    }
    private Boolean validateUsername() {
        String val = inputUsername.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            inputUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            inputUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            inputUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            inputUsername.setError(null);
            return true;
        }

    }
    //for signup save data in firebase

    public void saveDatabase() {


        rootnode = FirebaseDatabase.getInstance();
        reference=rootnode.getReference("UserDetails");
        //get the values
        String name=inputName.getText().toString();
        String dob=inputDateOfBirth.getText().toString();
        String phoneNo=inputPhoneNumber.getText().toString();
        String userName=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputpassword.getText().toString();
        String uId=FirebaseAuth.getInstance().getCurrentUser().getUid();



//        Query checkUser = reference.orderByChild("name");
//        String n = checkUser.getValue(String.class);
//        if(n==name){
//            inputName.setError("name already exist");
//        }

        UserHelper helperclass=new UserHelper(name,phoneNo,dob,userName,email,password);
        reference.child(uId).setValue(helperclass);

    }



}