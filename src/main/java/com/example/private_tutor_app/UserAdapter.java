package com.example.private_tutor_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<User> userList;

    public UserAdapter(UserChat userChat, int line_user_chat, ArrayList<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    private class ViewHolder{
        CircleImageView imgAvatar;
        TextView txtFullname, txtEmail;
    }
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            holder.imgAvatar = view.findViewById(R.id.imgAvatar);
            holder.txtFullname = view.findViewById(R.id.txtFullnam);
            holder.txtEmail = view.findViewById(R.id.txtEmail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        User user = userList.get(i);

        holder.txtFullname.setText(user.getFullname());
        holder.txtEmail.setText(user.getEmail());
        holder.imgAvatar.setImageBitmap(getUserImage(user.getImageUrl()));

        return view;
    }
}
