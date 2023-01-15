package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CreatedClassDetail extends AppCompatActivity {

    TextView txtDes, txtReq, txtSub, txtFee, txtAdd, txtGrade, txtDate, txtTimes;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_class_detail);

        txtDes = findViewById(R.id.txtDes);
        txtGrade = findViewById(R.id.grade);
        txtSub = findViewById(R.id.subject);
        txtFee= findViewById(R.id.fee);
        txtReq = findViewById(R.id.requirement);
        txtAdd = findViewById(R.id.address);
        txtDate = findViewById(R.id.date);
        txtTimes = findViewById(R.id.times);
        imgBack = findViewById(R.id.icBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreatedClassDetail.this, Parent_Board.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Class classTutor = (Class) intent.getSerializableExtra("dataClass");

        txtDes.setText(classTutor.getDescription());
        txtSub.setText(classTutor.getSubject());
        txtGrade.setText(classTutor.getGrade());
        txtFee.setText(classTutor.getFee());
        txtReq.setText(classTutor.getRequirement());
        txtAdd.setText(classTutor.getAddress());
        txtTimes.setText(classTutor.getTimes());
        txtDate.setText(classTutor.getDate());
    }
}