package com.example.private_tutor_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.private_tutor_app.R;
import com.example.private_tutor_app.TutorDetails;
import com.example.private_tutor_app.model.Tutor;

import java.util.List;

public class TutorAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Tutor> tutorList;

    public TutorAdapter(Context context, int layout, List<Tutor> tutorList) {
        this.context = context;
        this.layout = layout;
        this.tutorList = tutorList;
    }

    @Override
    public int getCount() {
        return tutorList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    private class ViewHolder{
        TextView txtFullname, txtGender, txtSchool, txtSubject
                , txtExperience, txtViewMore, txtEmail, txtPnumber
                , txtAdd, txtYob;
        ImageView imgAvatar, imgHeartOut, imgHeart;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.imgAvatar = view.findViewById(R.id.imgAvatar);
            holder.txtFullname = (TextView) view.findViewById(R.id.txtFullname);
            holder.txtGender = (TextView) view.findViewById(R.id.txtGender);
            holder.txtSchool = (TextView) view.findViewById(R.id.txtSchool);
            holder.txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            holder.txtExperience = (TextView) view.findViewById(R.id.txtExp);
            holder.txtViewMore = (TextView) view.findViewById(R.id.txtViewMore);
            holder.txtEmail = (TextView) view.findViewById(R.id.email);
            holder.txtPnumber = (TextView) view.findViewById(R.id.pNumber);
            holder.txtAdd = (TextView) view.findViewById(R.id.address);
            holder.txtYob = (TextView) view.findViewById(R.id.yob);
            holder.imgHeartOut = view.findViewById(R.id.imgHeartOutline);
            holder.imgHeart = view.findViewById(R.id.imgHeart);
            view.setTag(holder);
        } else {
            holder = (TutorAdapter.ViewHolder) view.getTag();
        }
        Tutor tutor = tutorList.get(i);

        holder.txtFullname.setText(tutor.getFullName());
        holder.txtGender.setText(tutor.getGender());
        holder.txtSchool.setText(tutor.getSchool());
        holder.txtSubject.setText(tutor.getSubject());
        holder.txtExperience.setText(tutor.getExperience());
        if(tutor.getImageUrl().equals("default")){
            holder.imgAvatar.setImageResource(R.drawable.img_5);
        } else {
            Glide.with(context).load(tutor.getImageUrl()).into(holder.imgAvatar);
        }

        holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TutorDetails.class);
                intent.putExtra("dataTutor", tutor);
                context.startActivity(intent);
            }
        });

        holder.imgHeartOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgHeart.setVisibility(View.VISIBLE);
                holder.imgHeartOut.setVisibility(View.INVISIBLE);
            }
        });
        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imgHeart.setVisibility(View.INVISIBLE);
                holder.imgHeartOut.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}
