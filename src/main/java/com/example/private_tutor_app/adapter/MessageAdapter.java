package com.example.private_tutor_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.private_tutor_app.ChatActivity;
import com.example.private_tutor_app.R;
import com.example.private_tutor_app.model.Message;
import com.example.private_tutor_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_RECEIVER = 0;
    public static final int MSG_TYPE_SENDER = 1;

    private Context mContext;
    private List<Message> mMessages;
    private String imgUrl;

    FirebaseUser fUser;

    public MessageAdapter(Context mContext, List<Message> mMessages, String imgUrl) {
        this.mContext = mContext;
        this.mMessages = mMessages;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RECEIVER){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_received_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_sent_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Message message = mMessages.get(position);

        holder.show_message.setText(message.getMessage());

        try{
            if(imgUrl.equals("default")){
                holder.img_avatar.setImageResource(R.drawable.ic_person_o);
            } else {
                Glide.with(mContext).load(imgUrl).into(holder.img_avatar);
            }
        } catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView img_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            img_avatar = itemView.findViewById(R.id.img_avatar);
        }
    }

    @Override
    public int getItemViewType(int position){
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mMessages.get(position).getSender().equals(fUser.getUid())){
            return MSG_TYPE_SENDER;
        } else { return MSG_TYPE_RECEIVER;}
    }
}
