package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update_Tutor_Infor extends AppCompatActivity {
    ImageView imgAvatar;
    EditText edtFullname, edtEmail, edtPhonenumber, edtAddress, edtSchool, edtExp, edtYoB, edtSubject;
    RadioGroup rdGender;
    RadioButton rdMale, rdFemale;
    String fullName, email;
    Button btnUpdate, btnCancel;
    String gender = "";
    String urlUpdate = Constants.BASE_URL + "Tutor_app/updateTutor.php";
    String urlGetTutor = Constants.BASE_URL + "Tutor_app/getTutor.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tutor_infor);

        imgAvatar = findViewById(R.id.image);
        edtFullname = findViewById(R.id.edtFullname);
        edtEmail = findViewById(R.id.edtEmail);
        edtYoB = findViewById(R.id.edtYoB);
        edtSubject = findViewById(R.id.edtSubject);
        edtPhonenumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        edtSchool = findViewById(R.id.edtSchool);
        edtExp = findViewById(R.id.edtExperience);
        rdGender = findViewById(R.id.radio_gender);
        rdMale = findViewById(R.id.radio_Malee);
        rdFemale = findViewById(R.id.radio_Femalee);
        btnUpdate = findViewById(R.id.btnSignup);
        btnCancel = findViewById(R.id.btnCancel);

        rdGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_Malee:
                        break;
                    case R.id.radio_Femalee:
                        break;
                }
            }
        });

        StrictMode.enableDefaults();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Update_Tutor_Infor.this, Tutor_Profile.class));
                finish();
            }
        });
        getTutorData(urlGetTutor);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String fullname = edtFullname.getText().toString();
                    String email = edtEmail.getText().toString();
                    String yob = edtYoB.getText().toString();
                    String subject = edtSubject.getText().toString();
                    String phonenumber = edtPhonenumber.getText().toString();
                    String Address = edtAddress.getText().toString();
                    String school = edtSchool.getText().toString();
                    String exp = edtExp.getText().toString();
                    if(rdMale.isChecked()){
                        gender = "Male";
                    } else if(rdFemale.isChecked()){
                        gender = "Female";
                    }
                    if( !fullname.equals(null) && !email.equals(null) && !yob.equals(null)&& !subject.equals(null)&& !phonenumber.equals(null)
                            && !Address.equals(null)&& !school.equals(null)&& !exp.equals(null)) {
                        UpdateTutor(urlUpdate);
                        startActivity(new Intent(Update_Tutor_Infor.this, Tutor_Profile.class));
                        finish();
                    } else
                        Toast.makeText(Update_Tutor_Infor.this, "Fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getTutorData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getInt("Id_Tutor") == Constants.ID_TUTOR){
                                    edtFullname.setText(object.getString("Fullname"));
                                    edtEmail.setText(object.getString("Email"));
                                    edtExp.setText(object.getString("Experience"));
                                    edtSchool.setText(object.getString("School"));
                                    edtSubject.setText(object.getString("Subject"));
                                    edtYoB.setText(object.getString("YoB"));
                                    edtAddress.setText(object.getString("Address"));
                                    edtPhonenumber.setText(object.getString("Phonenumber"));
                                    String gen = object.getString("Gender");
                                    if(gen.equals("male") || gen.equals("Male")){
                                        rdMale.setChecked(true);
                                    } else rdFemale.setChecked(true);
                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Tutor_Infor.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void UpdateTutor(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Update successfully")){
                            Toast.makeText(Update_Tutor_Infor.this, "Update tutor information successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Update_Tutor_Infor.this, "Fail to update tutor information", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Tutor_Infor.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_tutor", String.valueOf(Constants.ID_TUTOR));
                param.put("fullname", edtFullname.getText().toString().trim());
                param.put("email", edtEmail.getText().toString().trim());
                param.put("YoB", edtYoB.getText().toString().trim());
                param.put("subject", edtSubject.getText().toString().trim());
                param.put("phonenumber", edtPhonenumber.getText().toString().trim());
                param.put("address", edtAddress.getText().toString().trim());
                param.put("school", edtSchool.getText().toString().trim());
                param.put("experience", edtExp.getText().toString().trim());
                param.put("gender", gender);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}