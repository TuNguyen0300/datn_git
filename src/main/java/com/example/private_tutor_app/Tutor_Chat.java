package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.private_tutor_app.adapter.UserAdapter;
import com.example.private_tutor_app.model.Message;
import com.example.private_tutor_app.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tutor_Chat extends AppCompatActivity {

    BottomNavigationView nav;

    RecyclerView recyclerView;

    UserAdapter userAdapter;
    ArrayList<User> mUsers;

    FirebaseUser fUser;
    DatabaseReference reference;

    List<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_chat);
        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        recyclerView = findViewById(R.id.listUser);

        nav.setSelectedItemId(R.id.navigation_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        Intent i = new Intent(Tutor_Chat.this, Tutor_Home.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_forum:
                        Intent i1 = new Intent(Tutor_Chat.this, Tutor_DashBoard.class);
                        startActivity(i1);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        return true;
                    case R.id.navigation_profile:
                        Intent i2 = new Intent(Tutor_Chat.this, Tutor_Profile.class);
                        startActivity(i2);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.listUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);
                    assert message != null;
                    if(message.getSender().equals(fUser.getUid())){
                        userList.add(message.getReceiver());
                    }
                    if(message.getReceiver().equals(fUser.getUid())){
                        userList.add(message.getSender());
                    }
                }
                for(int i=0; i<userList.size(); i++){
                    for(int j=userList.size()-1; j>i; j--){
                        if(userList.get(i).equals(userList.get(j))){
                            userList.remove(userList.get(j));
                        }
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Tutor_Chat.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void readChats(){
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    for (int i=0; i<userList.size(); i++){
                        if(user.getId().equals(userList.get(i))){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getApplicationContext(), mUsers);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Tutor_Chat.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}