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
import com.bumptech.glide.Glide;
import com.example.private_tutor_app.model.Tutor;
import com.example.private_tutor_app.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TutorDetails extends AppCompatActivity {

    TextView txtFName, txtEmail, txtPNumber, txtAddr, txtSchool, txtSub, txtExp, txtYoB, txtGender;
    ImageView imgAvatar, imgContact, imgBack, imgHeart, imgHeartOut;

    String urlInsertFav = Constants.BASE_URL + "Tutor_app/favTutorAdd.php";
    String urlGetFavTutor = Constants.BASE_URL + "Tutor_app/favTutor.php";
    String urlDeleteFavTutor = Constants.BASE_URL + "Tutor_app/favTutorDel.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_details);

        txtFName = findViewById(R.id.fullname);
        txtEmail = findViewById(R.id.email);
        txtPNumber = findViewById(R.id.pNumber);
        txtAddr = findViewById(R.id.address);
        txtSchool = findViewById(R.id.school);
        txtSub = findViewById(R.id.subject);
        txtExp = findViewById(R.id.exp);
        txtYoB = findViewById(R.id.yob);
        txtGender = findViewById(R.id.gender);
        imgBack = findViewById(R.id.icBack);
        imgContact = findViewById(R.id.imgContact);
        imgAvatar = findViewById(R.id.imgAvatar);
        imgHeart = findViewById(R.id.imgHeart);
        imgHeartOut = findViewById(R.id.imgHeartOutline);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TutorDetails.this, Parent_Home.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Tutor tutors = (Tutor) intent.getSerializableExtra("dataTutor");

        txtFName.setText(tutors.getFullName());
        txtEmail.setText(tutors.getEmail());
        txtPNumber.setText(tutors.getPhoneNumber());
        txtAddr.setText(tutors.getAddress());
        txtYoB.setText(tutors.getYob());
        txtGender.setText(tutors.getGender());
        txtSub.setText(tutors.getSubject());
        txtSchool.setText(tutors.getSchool());
        txtExp.setText(tutors.getExperience());

        if (tutors.getImageUrl().equals("default")) {
            imgAvatar.setImageResource(R.drawable.img_5);
        } else {
            Glide.with(this).load(tutors.getImageUrl()).into(imgAvatar);
        }

        imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(TutorDetails.this, tutors.getId_user(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(TutorDetails.this, ChatActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("userId", tutors.getId_user());
                startActivity(intent1);
            }
        });

        GetFav(urlGetFavTutor, tutors.getId_tutor());

        imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelFav(urlDeleteFavTutor, tutors.getId_tutor());
            }
        });

        imgHeartOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFav(urlInsertFav, tutors.getId_tutor());
            }
        });

    }
    public void GetFav(String url, int idTut){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            int count = 0;
                            for(int i=0; i< response.length(); i++){
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    String id_parent =  object.getString("Id_Parent");
                                    String id_tutor = object.getString("Id_Tutor");
                                    if( id_parent.equals(String.valueOf(Constants.ID_PARENT))
                                            && id_tutor.equals(String.valueOf(idTut)) ){
                                        count++;
                                    }
                                } catch (Exception e){
                                    Toast.makeText(TutorDetails.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(TutorDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
    }
    public void AddFav(String url, int idTut){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success insert")) {
                            imgHeartOut.setVisibility(View.INVISIBLE);
                            imgHeart.setVisibility(View.VISIBLE);
                            Toast.makeText(TutorDetails.this, "Add favourite tutor successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TutorDetails.this, "Fail to add favourite tutor", Toast.LENGTH_SHORT).show();
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
                param.put("id_parent", String.valueOf(Constants.ID_PARENT));
                param.put("id_tutor", String.valueOf(idTut));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void CancelFav(String url, int idTut){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                        Toast.makeText(TutorDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_parent", String.valueOf(Constants.ID_PARENT));
                param.put("id_tutor", String.valueOf(idTut));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
