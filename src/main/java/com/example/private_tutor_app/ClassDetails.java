package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.model.Class;
import com.example.private_tutor_app.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClassDetails extends AppCompatActivity {

    TextView txtDes, txtReq, txtSub, txtFee, txtAdd, txtGrade, txtDate, txtTimes;
    ImageView imgBack, imgContact, imgHeart, imgHeartOut;

    String urlInsertFav = Constants.BASE_URL + "Tutor_app/favClassAdd.php";
    String urlGetFav = Constants.BASE_URL + "Tutor_app/favClass.php";
    String urlDeleteFav = Constants.BASE_URL + "Tutor_app/favClassDel.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        txtDes = findViewById(R.id.txtDes);
        txtGrade = findViewById(R.id.grade);
        txtSub = findViewById(R.id.subject);
        txtFee= findViewById(R.id.fee);
        txtReq = findViewById(R.id.requirement);
        txtAdd = findViewById(R.id.address);
        txtDate = findViewById(R.id.date);
        txtTimes = findViewById(R.id.times);
        imgBack = findViewById(R.id.icBack);
        imgContact = findViewById(R.id.icContact);
        imgHeart = findViewById(R.id.imgHeart);
        imgHeartOut = findViewById(R.id.imgHeartOutline);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClassDetails.this, Tutor_Home.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Class classTutor = (Class) intent.getSerializableExtra("dataClass");

        txtDes.setText(classTutor.getDescription());
        txtSub.setText(classTutor.getSubject());
        txtGrade.setText(classTutor.getGrade());
        txtFee.setText(classTutor.getFee());
        txtReq.setText(classTutor.getRequirement());
        txtAdd.setText(classTutor.getAddress());
        txtTimes.setText(classTutor.getTimes());
        txtDate.setText(classTutor.getDate());

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ClassDetails.this, ChatActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("userId", classTutor.getId_parent());
                startActivity(intent1);
            }
        });

        GetFav(classTutor.getId_class());

        imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFav(classTutor.getId_class());
            }
        });

        imgHeartOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFav(classTutor.getId_class());
            }
        });
    }
    public void GetFav(int idC){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetFav, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String id_class =  object.getString("Id_Class");
                                String id_tutor = object.getString("Id_Tutor");
                                if( id_tutor.equals(String.valueOf(Constants.ID_TUTOR))
                                        && id_class.equals(String.valueOf(idC)) ){
                                    count++;
                                }
                            } catch (Exception e){
                                Toast.makeText(ClassDetails.this, e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        if( count == 1){
                            imgHeart.setVisibility(View.VISIBLE);
                            imgHeartOut.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClassDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public void AddFav(int idC){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertFav,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success insert")) {
                            imgHeartOut.setVisibility(View.INVISIBLE);
                            imgHeart.setVisibility(View.VISIBLE);
                            Toast.makeText(ClassDetails.this, "Add favourite class successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ClassDetails.this, "Fail to add favourite class", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_tutor", String.valueOf(Constants.ID_TUTOR));
                param.put("id_class", String.valueOf(idC));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void RemoveFav(int idC){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteFav,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("Delete fav successfully")){
                            imgHeartOut.setVisibility(View.VISIBLE);
                            imgHeart.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClassDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_tutor", String.valueOf(Constants.ID_TUTOR));
                param.put("id_class", String.valueOf(idC));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}