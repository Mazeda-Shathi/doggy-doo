package com.example.doggydo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doggydo.ml.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class classify extends AppCompatActivity {

    Button predict;
    TextView result;
    CardView capture,select;
   CircleImageView classifyImage;
    Bitmap bitmap;
    ImageView back;
    Uri uri,Photo_Uri,selectedImageUri;
    FirebaseUser mUser;

    ProgressDialog mloadingBar;
    DatabaseReference databaseReference;
    // for pagination

    String useridGlobal;
    String resultClass, resultProbability;
    Bitmap bitmapGlobal;
    StorageReference dog_image_storage_ref;
    DatabaseReference classification_history_ref;
    SharedPreferences preferences;
    long i;
    String ClassName,percentage;
    //end for pagination


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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
     FirebaseUser mUser = mAuth.getCurrentUser();
     useridGlobal=mUser.getUid();
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
                    //double pro = probability.get(index).getScore();
//                    String res = null;
//                     res = String.format("%.2f");
                    mloadingBar.dismiss();
                    result.setText("It's "+dog+" with probability "+find+"%");
                    ClassName = "Dog Class Name:"+dog;
                    percentage= String.valueOf(find);


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

                    Photo_Uri=uri;
                    bitmapGlobal = bitmap;
                 //   Toast.makeText(this, "uri is"+ uri, Toast.LENGTH_SHORT).show();

                    storeHistoryOnDatabase();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==12){
            File Imagefile=new File(getCacheDir(),"images");
                selectedImageUri = null;
           try{


               bitmap=(Bitmap) data.getExtras().get("data");
               classifyImage.setImageBitmap(bitmap);

               //for uri
               Imagefile.mkdirs();
               File file=new File(Imagefile,"captured_images");
               FileOutputStream stream=new FileOutputStream(file);
               bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
               stream.flush();
               stream.close();
               selectedImageUri= FileProvider.getUriForFile(this.getApplicationContext(),"com.example.doggydo"+".provider",file);
               Toast.makeText(this, "uri is"+ selectedImageUri, Toast.LENGTH_SHORT).show();
               Photo_Uri=selectedImageUri;
               bitmapGlobal = bitmap;
               storeHistoryOnDatabase();
               }


           catch(Exception e){
               e.printStackTrace();
           }

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
    public void storeHistoryOnDatabase() {
//        //for keeping last node value in sharedpreference
//
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("classification_history");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i= snapshot.child(useridGlobal).getChildrenCount();
                i++;
                // Log.d(String.valueOf(i) , "lastnodevalue");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //end for keeping last node value in sharedpreference

        dog_image_storage_ref = FirebaseStorage.getInstance().getReference().child("dog_image");
        classification_history_ref = FirebaseDatabase.getInstance().getReference().child("classification_history");//also be used to fetch postdata

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);
        dog_image_storage_ref.child(useridGlobal).child(String.valueOf(i)).putFile(Photo_Uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                if(task.isSuccessful()){
                    try {


                        dog_image_storage_ref.child(useridGlobal).child(String.valueOf(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //first put hashmap then into database


                                HashMap hashMap = new HashMap();

                                hashMap.put("DogImageUri", uri.toString());
                                hashMap.put("DogClass", ClassName);
                                hashMap.put("Probability", percentage);

                                classification_history_ref.child(useridGlobal).child(String.valueOf(i)).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {

                                            //Toast.makeText(classify.this, "Post Added", Toast.LENGTH_SHORT).show();


                                        } else {
                                            Toast.makeText(classify.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{

                    Toast.makeText(classify.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }

            }



        });
    }



}


