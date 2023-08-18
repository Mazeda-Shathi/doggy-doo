package com.example.doggydo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class postAPI extends AppCompatActivity {
    private RecyclerView recyclerView;
    JSONPlaceholder jsonPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        recyclerView = findViewById(R.id.recycerlview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholder = retrofit.create(JSONPlaceholder.class);
        getPost();
    }
    public void getPost(){
        Call<List<Post>> call = jsonPlaceholder.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(postAPI.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Post> postList = response.body();
                PostAdapter postAdapter = new PostAdapter(postAPI.this , postList);
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(postAPI.this, t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}