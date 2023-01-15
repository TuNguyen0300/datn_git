package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorDetails extends AppCompatActivity {

    TextView txtFName, txtEmail, txtPNumber, txtAddr, txtSchool, txtSub, txtExp, txtYoB, txtGender;
    ImageView imgContact, imgBack;

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

    }
}