package com.example.private_tutor_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        TextView txtFullname, txtGender, txtSchool, txtSubject, txtExperience, txtViewMore, txtEmail, txtPnumber, txtAdd, txtYob;
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

        holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TutorDetails.class);
                intent.putExtra("dataTutor", tutor);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
