package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.private_tutor_app.model.Tutor;

public class TutorDetails extends AppCompatActivity {

    TextView txtFName, txtEmail, txtPNumber, txtAddr, txtSchool, txtSub, txtExp, txtYoB, txtGender;
    ImageView imgAvatar, imgContact, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_details);

        txtFName = findViewById(R.id.fullname);
        txtEmail = findViewById(R.id.email);
        txtPNumber = findViewById(R.id.pNumber);
        txtAddr = findViewById(R.id.address);
        txtSchool = findViewById(R.id.school);
        txtSub = findViewById(R.id.subject);
        txtExp = findViewById(R.id.exp);
        txtYoB = findViewById(R.id.yob);
        txtGender = findViewById(R.id.gender);
        imgBack = findViewById(R.id.icBack);
        imgContact = findViewById(R.id.imgContact);
        imgAvatar = findViewById(R.id.imgAvatar);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TutorDetails.this, Parent_Home.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Tutor tutors = (Tutor) intent.getSerializableExtra("dataTutor");

        txtFName.setText(tutors.getFullName());
        txtEmail.setText(tutors.getEmail());
        txtPNumber.setText(tutors.getPhoneNumber());
        txtAddr.setText(tutors.getAddress());
        txtYoB.setText(tutors.getYob());
        txtGender.setText(tutors.getGender());
        txtSub.setText(tutors.getSubject());
        txtSchool.setText(tutors.getSchool());
        txtExp.setText(tutors.getExperience());

        if(tutors.getImageUrl().equals("default")){
            imgAvatar.setImageResource(R.drawable.img_5);
        } else {
            Glide.with(this).load(tutors.getImageUrl()).into(imgAvatar);
        }

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(TutorDetails.this, tutors.getId_user(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(TutorDetails.this, ChatActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("userId", tutors.getId_user());
                startActivity(intent1);
            }
        });

    }
}