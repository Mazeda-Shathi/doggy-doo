package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

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
    FirebaseUser mUser;
    CircleImageView Proimg;
    ProgressDialog mloadingBar;
    TextView thanaTv,districtTv;
//for spinner
private String selectedDistrict, selectedState;                 //vars to hold the values of selected State and District
    private TextView tvStateSpinner, tvDistrictSpinner;             //declaring TextView to show the errors
    private Spinner stateSpinner, districtSpinner;                  //Spinners
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;   //declare adapters for the spinners




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
        thanaTv=findViewById(R.id.thanaTextView);
        districtTv=findViewById(R.id.districtTextView);

        btnregSubmit=findViewById(R.id.btnRegSub);
        gotolog=findViewById(R.id.btngotoLog);
        mAuth=FirebaseAuth.getInstance();


         //for date
        datepicker=findViewById(R.id.regdob);
        final Calendar calendar=Calendar.getInstance();
       final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        mloadingBar=new ProgressDialog(this);
        TextWatcher watcherPhnNumber=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=inputPhoneNumber.getText().toString();
                String phonNumberpattern="[0][1][^012]{1}[0-9]{8}";
                if(val.isEmpty()){
                    inputPhoneNumber.setError("Field cannot be empty");

                }
                else if(!val.matches(phonNumberpattern)){
                    inputPhoneNumber.setError("Invalid Phone Number");

                }
                else{
                    inputPhoneNumber.setError(null);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        inputPhoneNumber.addTextChangedListener(watcherPhnNumber);

        TextWatcher watcherEmail=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val=inputEmail.getText().toString();
                String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(val.isEmpty()){
                    inputEmail.setError("Field cannot be empty");
                }
                else if(!val.matches(emailPattern)){
                    inputEmail.setError("Invalid email address");
                }

                else{
                    inputEmail.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        inputEmail.addTextChangedListener(watcherEmail);

        TextWatcher watcherUserName=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val = inputUsername.getText().toString();
                String noWhiteSpace = "\\A\\w{4,20}\\z";
                rootnode = FirebaseDatabase.getInstance();
                reference=rootnode.getReference("UserDetails");
                reference.orderByChild("userName").equalTo(val).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            inputUsername.setError("Username already exists");
                        }  else {
                            inputUsername.setError(null);
//                           inputUsername.setErrorEnabled(false);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                if (val.isEmpty()) {
                    inputUsername.setError("Field cannot be empty");
                } else if (val.length() >= 15) {
                    inputUsername.setError("Username too long");
                } else if (!val.matches(noWhiteSpace)) {
                    inputUsername.setError("White Spaces are not allowed");
                } else {
                    inputUsername.setError(null);
                }

            }
        };
        inputUsername.addTextChangedListener(watcherUserName);

      //  for date of birth
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

        //......for district...../////
        //State Spinner Initialisation
        stateSpinner = findViewById(R.id.spinnerBangladeshState);

        //Populate ArrayAdapter using string array and a spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_bangladeshi_state, R.layout.spinner_layout);

        // Specify the layout to use when the list of choices appear
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(stateAdapter);          //to populate the State Spinner
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtSpinner=findViewById(R.id.spinnerBangladeshDistrict);
                selectedState=stateSpinner.getSelectedItem().toString();
                int parentId= ((ViewGroup)view.getParent()).getId();
                if(parentId == R.id.spinnerBangladeshState){
                    switch (selectedState){
                        case "Select your District":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.spinner_layout);
                            break;
                        case "Chittagong":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.Chittagong, R.layout.spinner_layout);
                            break;
                        case "Dhaka":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.Dhaka, R.layout.spinner_layout);
                            break;
                        case "Rajshahi":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.Rajshahi, R.layout.spinner_layout);
                            break;

                        default:break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpinner.setAdapter(districtAdapter);
                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict=districtSpinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                    if(!validateName()   | !validatePassword() | !validatedob() )

                    {
                        return;

                    }
                    else{
                        mloadingBar.setTitle("Wait for registration");
                        mloadingBar.setCanceledOnTouchOutside(false);
                        mloadingBar.show();
                    }
                    String em=inputEmail.getText().toString();
                    String pa=inputpassword.getText().toString();

//                if (selectedState.equals("Select Your State")) {
//                   // Toast.makeText(MainActivity.this, "Please select your state from the list", Toast.LENGTH_LONG).show();
//                    districtTv.setError("State is required!");      //To set error on TextView
//                   districtTv.requestFocus();
//                } else if (selectedDistrict.equals("Select Your District")) {
//                 thanaTv.setError("District is required!");
//                    thanaTv.requestFocus();
//                    thanaTv.setError(null);                      //To remove error from stateSpinner
//                } else {
//                   thanaTv.setError(null);
//                   districtTv.setError(null);
//                }

                    mAuth.createUserWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                mloadingBar.dismiss();
                                Toast.makeText(Signup.this,"Registration is successfull",Toast.LENGTH_SHORT).show();
                                saveDatabase();
                                Intent intent=new Intent(Signup.this,UpdateProfileImage.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                mloadingBar.dismiss();
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


        UserHelper helperclass=new UserHelper(name,phoneNo,dob,userName,email,password,selectedDistrict,selectedState);
        reference.child(uId).setValue(helperclass);



            }








}