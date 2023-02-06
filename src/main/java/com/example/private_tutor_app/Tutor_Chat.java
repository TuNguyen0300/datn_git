package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Tutor_Chat extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton newChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_chat);
        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        newChat = findViewById(R.id.fabNewchat);
        nav.setSelectedItemId(R.id.navigation_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(Tutor_Chat.this, Tutor_Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(Tutor_Chat.this, Tutor_DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Tutor_Chat.this, Tutor_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tutor_Chat.this, UserChat.class));
                finish();
            }
        });
    }
}