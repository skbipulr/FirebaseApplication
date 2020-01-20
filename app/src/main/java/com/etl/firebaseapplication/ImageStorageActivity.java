package com.etl.firebaseapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageStorageActivity extends AppCompatActivity {
    private LinearLayout addL;
    private RelativeLayout showL;
    private ImageView cancelIV,showImageTV;
    private StorageReference storageReference;
    private String imageUrl ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_storage);

        addL = findViewById(R.id.addL);
        showL = findViewById(R.id.showL);
        cancelIV = findViewById(R.id.clearBtn);
        showImageTV = findViewById(R.id.showImageIV);

        storageReference = FirebaseStorage.getInstance().getReference();

        addL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addL.setVisibility(View.VISIBLE);
                showL.setVisibility(View.GONE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            if (data!=null){
                Uri imageUri = data.getData();

                addL.setVisibility(View.GONE);
                showL.setVisibility(View.VISIBLE);
                showImageTV.setImageURI(imageUri);
                uploadFile(imageUri);
            }
        }




    }

    private void uploadFile(Uri imageUri) {
        /*final ProgressDialog progressDialog = new ProgressDialog(ImageStorageActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(true);
        progressDialog.show();*/

        final StorageReference imageRef = storageReference.child("profileImage").child(String.valueOf(System.currentTimeMillis()));
        imageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                           // progressDialog.dismiss();
                        }
                    });
                }
               // progressDialog.dismiss();
            }
        });
    }
}
