package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.private_tutor_app.adapter.MessageAdapter;
import com.example.private_tutor_app.model.Message;
import com.example.private_tutor_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    TextView txtFullname;

    FirebaseUser fUser;
    DatabaseReference reference;

    ImageButton btnSend;
    EditText txtInput;
    ImageView imgBack;

    MessageAdapter messageAdapter;
    List<Message> mMesssages;

    RecyclerView recyclerView;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtFullname = findViewById(R.id.Fullname);
        txtInput = findViewById(R.id.inputMessage);
        btnSend = findViewById(R.id.btnSend);
        imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.chatRecyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        intent = getIntent();
        String userId = intent.getStringExtra("userId");

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mes = txtInput.getText().toString();
                if(!mes.equals("")){
                    sendMessage(fUser.getUid(), userId, mes);
                } else {
                    Toast.makeText(ChatActivity.this, "You can not send an empty message", Toast.LENGTH_SHORT).show();
                }
                txtInput.setText("");

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                txtFullname.setText(user.getFullname());

                readMessage(fUser.getUid(), userId, user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void readMessage(String myId, String userId, String imgUrl){
        mMesssages = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMesssages.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Message message = snapshot.getValue(Message.class);
                    if(( message.getReceiver().equals(myId) && message.getSender().equals(userId) )
                        || ( message.getReceiver().equals(userId) && message.getSender().equals(myId) )){
                        mMesssages.add(message);
                    }
                }
                messageAdapter = new MessageAdapter(ChatActivity.this, mMesssages, imgUrl);
                recyclerView.setAdapter(messageAdapter);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}