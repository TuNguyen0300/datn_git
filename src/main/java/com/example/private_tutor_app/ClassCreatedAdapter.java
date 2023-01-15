package com.example.private_tutor_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClassCreatedAdapter extends BaseAdapter {

    private Parent_Board context;
    private int layout;
    private List<Class> classList;

    public ClassCreatedAdapter(Parent_Board context, int layout, List<Class> classList) {
        this.context = context;
        this.layout = layout;
        this.classList = classList;
    }

    @Override
    public int getCount() {
        return classList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtDescript, txtRequire, txtSubject, txtFee, txtAddress, txtDate, txtViewMore, txtGrade, txtTimes;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtDescript = (TextView) view.findViewById(R.id.txtDescript);
            holder.txtRequire = (TextView) view.findViewById(R.id.txtRequire);
            holder.txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            holder.txtFee = (TextView) view.findViewById(R.id.txFee);
            holder.txtAddress= (TextView) view.findViewById(R.id.txtAddress);
            holder.txtDate= (TextView) view.findViewById(R.id.txtCDate);
            holder.txtGrade= (TextView) view.findViewById(R.id.grade);
            holder.txtTimes= (TextView) view.findViewById(R.id.times);
            holder.txtViewMore= (TextView) view.findViewById(R.id.txtViewMore);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imEdit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Class classTutor = classList.get(i);

        holder.txtDescript.setText(classTutor.getDescription());
        holder.txtRequire.setText("Requirement: " + classTutor.getRequirement());
        holder.txtSubject.setText(classTutor.getSubject());
        holder.txtFee.setText(classTutor.getFee());
        holder.txtAddress.setText(classTutor.getAddress());
        holder.txtDate.setText(classTutor.getDate());


        holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreatedClassDetail.class);
                intent.putExtra("dataClass", classTutor);
                context.startActivity(intent);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(context, Parent_edit_class.class);
                intents.putExtra("dataClass", classTutor);
                context.startActivity(intents);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete(classTutor.getDescription(), classTutor.getId_class());
            }
        });
        return view;
    }
    private void confirmDelete(String des, int idClass){
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(context);
        dialogDel.setMessage("Do you really want to delete: "+ des +" ?");
        dialogDel.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteClass(idClass);
            }
        });
        dialogDel.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDel.show();
    }
}
