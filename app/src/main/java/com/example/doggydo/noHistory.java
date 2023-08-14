package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class noHistory extends AppCompatActivity {
 Button clss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_history);
        clss=findViewById(R.id.classifyForHistory);
        clss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(noHistory.this,classify.class);
                startActivity(i);
            }
        });
    }

}