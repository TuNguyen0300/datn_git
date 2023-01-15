package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.private_tutor_app.utilities.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Parent_Chat extends AppCompatActivity {
    BottomNavigationView nav;
    FloatingActionButton newChat;
    PreferenceManager preferenceManager;
    EditText edtE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chat);
        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        newChat = findViewById(R.id.fabNewchat);
        preferenceManager = new PreferenceManager(this);
        nav.setSelectedItemId(R.id.navigation_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(Parent_Chat.this, Parent_Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(Parent_Chat.this, Parent_Board.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Parent_Chat.this, Parent_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        edtE = findViewById(R.id.edtError);

        getToken();
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_Chat.this, UserChat.class));
                finish();
            }
        });

    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void getToken (){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void updateToken(String token){
        Toast.makeText(this, Constants.ID_USER, Toast.LENGTH_SHORT).show();
        try {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            DocumentReference documentReference = database.collection("Users").document(preferenceManager.getString("id"));
            documentReference.update("fcmToken", token)
                    .addOnSuccessListener(unused -> {showToast("Update token successfully");})
                    .addOnFailureListener(e -> {showToast("fail");});
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            edtE.setText(e.toString());
        }
    }
    private void clear(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database
                .collection("Users").document(preferenceManager.getString("id"));
        HashMap<String, Object> updates = new HashMap<>();
        updates.put("fcmToken", FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(unused -> {preferenceManager.clear();})
                .addOnFailureListener(e -> {showToast("fail");});

    }
}