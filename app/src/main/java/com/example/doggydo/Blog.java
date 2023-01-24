package com.example.doggydo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Blog extends AppCompatActivity {

ImageView  addImagePost,sendImagePost;
EditText inputPostDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        addImagePost=findViewById(R.id.addImagePost);
        sendImagePost=findViewById(R.id.sendImagePost);
        inputPostDescription=findViewById(R.id.inputAddPost);

        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost();
            }
        });
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                StartActivityForResult(intent,10);
            }
        });
    }

    private void StartActivityForResult(Intent intent, int i) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AddPost() {
    }
}