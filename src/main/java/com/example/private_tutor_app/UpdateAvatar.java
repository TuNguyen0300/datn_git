package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateAvatar extends AppCompatActivity {

    CircleImageView imgAvatar;
    Button btnUpdate, btnCancle;
    ImageView icBack;
    TextView txtChange;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    Uri uriImage;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfile = FirebaseStorage.getInstance().getReference().child("Profile pics");

        imgAvatar = findViewById(R.id.imgAvatar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancle = findViewById(R.id.btnCancel);
        icBack = findViewById(R.id.icBack);

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateAvatar.this, Parent_Profile.class));
                finish();
            }
        });
        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateAvatar.this, Parent_Profile.class));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadProfileImage();
            }
        });
        //imgAvatar.setImageBitmap();
    }
    private void UploadProfileImage(){

    }
}