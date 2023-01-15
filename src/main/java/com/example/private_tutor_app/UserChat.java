package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.private_tutor_app.utilities.PreferenceManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserChat extends AppCompatActivity {
    ListView listUser;
    ArrayList<User> users;
    UserAdapter userAdapter;

    ImageView icBack;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        icBack = findViewById(R.id.icBack);
        preferenceManager = new PreferenceManager(this);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserChat.this, Parent_Chat.class));
                finish();
            }
        });

        listUser = findViewById(R.id.listUser);
        users = new ArrayList<>();
        userAdapter = new UserAdapter(this, R.layout.line_user_chat, users);
        getUser();
    }

    private void getUser(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("User").get()
                .addOnCompleteListener(this::onComplete);

    }

    private void onComplete(Task<QuerySnapshot> task) {
        String currentUserId = preferenceManager.getString("id");
        if (task.isSuccessful() && task.getResult() != null) {
            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                    continue;
                }
                User user = new User();
                user.setFullname(queryDocumentSnapshot.getString("fullname"));
                user.setEmail(queryDocumentSnapshot.getString("email"));
                user.setImageUrl(queryDocumentSnapshot.getString("imageUrl"));
                user.setToken(queryDocumentSnapshot.getString("fcmToken"));
                users.add(user);
            }
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this, users.toString(), Toast.LENGTH_SHORT).show();
            if (users.size() > 0) {
                listUser.setAdapter(userAdapter);
                listUser.setVisibility(View.VISIBLE);
            } else Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "error...", Toast.LENGTH_SHORT).show();
    }
}