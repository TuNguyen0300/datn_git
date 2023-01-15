package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Parent_create_class extends AppCompatActivity {

    String urlCreateClass = Constants.BASE_URL + "Tutor_app/createClass.php";

    EditText edtDes, edtAdd, edtSub, edtGrade, edtFee, edtTimes, edtReq;
    Button btnCreate, btnCancel;
    ImageView imBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_create_class);

        edtDes = findViewById(R.id.edtDescription);
        edtAdd = findViewById(R.id.edtAddress);
        edtSub = findViewById(R.id.edtSubject);
        edtGrade = findViewById(R.id.edtGrade);
        edtFee = findViewById(R.id.edtFee);
        edtTimes = findViewById(R.id.edtTimes);
        edtReq = findViewById(R.id.edtRequirement);
        btnCreate = findViewById(R.id.btnCreate);
        btnCancel = findViewById(R.id.btnCancel);
        imBack = findViewById(R.id.icBack);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_create_class.this, Parent_Board.class));
            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_create_class.this, Parent_Board.class));
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = edtDes.getText().toString().trim();
                String add = edtAdd.getText().toString().trim();
                String sub = edtSub.getText().toString().trim();
                String grade = edtGrade.getText().toString().trim();
                String fee = edtFee.getText().toString().trim();
                String req = edtReq.getText().toString().trim();
                String time = edtTimes.getText().toString().trim();

                if(des.equals(null) || add.equals(null) || sub.equals(null) || grade.equals(null) || fee.equals(null) || req.equals(null) || time.equals(null) ){
                    Toast.makeText(Parent_create_class.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                }
                if(des.equals(null)){
                    edtDes.setError("Please enter description"); edtDes.requestFocus();
                }
                if(des.length()>250 || des.length()<6){
                    Toast.makeText(Parent_create_class.this, "Description should be 6 - 255 characters", Toast.LENGTH_SHORT).show();
                }
                if(add.equals(null)){
                    edtAdd.setError("Please enter address"); edtAdd.requestFocus();
                }
                if(add.length()>100 || add.length()<6){
                    Toast.makeText(Parent_create_class.this, "Address should be 6 - 100 characters", Toast.LENGTH_SHORT).show();
                }
                if(sub.equals(null)){
                    edtSub.setError("Please enter description"); edtSub.requestFocus();
                }
                if(sub.length()>20){
                    Toast.makeText(Parent_create_class.this, "Subject should be under 20 characters", Toast.LENGTH_SHORT).show();
                }
                if(grade.equals(null)){
                    edtGrade.setError("Please enter description"); edtGrade.requestFocus();
                }
                if(fee.equals(null)){
                    edtFee.setError("Please enter description"); edtFee.requestFocus();
                }
                if(time.equals(null)){
                    edtTimes.setError("Please enter description"); edtTimes.requestFocus();
                }
                if(req.equals(null)){
                    edtReq.setError("Please enter description"); edtReq.requestFocus();
                }
                if(req.length()>250 || des.length()<6){
                    Toast.makeText(Parent_create_class.this, "Requirement should be 6 - 255 characters", Toast.LENGTH_SHORT).show();
                }
                if(!des.equals(null) && !add.equals(null) && !sub.equals(null) && !grade.equals(null) &&
                        !fee.equals(null) && !req.equals(null) && !time.equals(null) ){
                    CreateClass(urlCreateClass);
                }
            }
        });

    }
    public void CreateClass(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success create")){
                            Toast.makeText(Parent_create_class.this, "Create new class successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Parent_create_class.this, Parent_Home.class));
                        } else {
                            Toast.makeText(Parent_create_class.this, "Fail to create new class", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_create_class.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("cr_date", LocalDate.now().toString());
                param.put("description", edtDes.getText().toString().trim());
                param.put("Address", edtAdd.getText().toString().trim());
                param.put("subject", edtSub.getText().toString().trim());
                param.put("grade", edtGrade.getText().toString().trim());
                param.put("fee", edtFee.getText().toString().trim());
                param.put("number", edtTimes.getText().toString().trim() + "/week");
                param.put("requirement", edtReq.getText().toString().trim());
                param.put("id_parent", String.valueOf(Constants.ID_PARENT));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}