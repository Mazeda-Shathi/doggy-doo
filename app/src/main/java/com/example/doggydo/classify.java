package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doggydo.ml.Model;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class classify extends AppCompatActivity {

    Button predict;
    TextView result,capture,select;
   CircleImageView classifyImage;
    Bitmap bitmap;
    ImageView back;
    ProgressDialog mloadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        getPermission();
       capture=findViewById(R.id.capturebtn);
       back=findViewById(R.id.backHome);
        select=findViewById(R.id.selectImagebtn);
        predict=findViewById(R.id.predictionbtn);
        result=findViewById(R.id.result);
        classifyImage=findViewById(R.id.classifyImage);
        mloadingBar=new ProgressDialog(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(classify.this,HomePage.class);
                startActivity(intent);
            }
        });




        //for select image from set
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);

            }
        });
        //for capture

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,12);
            }
        });

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mloadingBar.setTitle("Wait");
                    mloadingBar.setCanceledOnTouchOutside(false);
                    mloadingBar.show();
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorImage image = TensorImage.fromBitmap(bitmap);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(image);
                    List<Category> probability = outputs.getProbabilityAsCategoryList();
                    int index=getMax(probability);
                    String dog=probability.get(index).getLabel().substring(10);
                    float find=probability.get(index).getScore()*100;
//                    String res = null;
//                     res = String.format("%.2f");
                    mloadingBar.dismiss();
                    result.setText("It's "+dog+" with probability "+find+"%");

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }
        });
    }
    public int getMax(List<Category> prob){
        float max=0;
        int index=0;
        for(int i=0;i<prob.size();i++){
            if(max<=prob.get(i).getScore()){
                max=prob.get(i).getScore();
                index=i;
            }
        }
        return index;
    }
    //for select image from set
    protected void onActivityResult(int requestCode , int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri uri=data.getData();
                try{
                    bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    classifyImage.setImageBitmap(bitmap);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
        else if(requestCode==12){
            bitmap=(Bitmap) data.getExtras().get("data");
            classifyImage.setImageBitmap(bitmap);
        }

        super.onActivityResult(resultCode,requestCode,data);

    }
///end of slectimage

    //start (camera)
   public void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(classify.this,new String[]{Manifest.permission.CAMERA},11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11) {
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}


