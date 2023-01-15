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

public class Parent_edit_class extends AppCompatActivity {

    String urlUpdateClass = Constants.BASE_URL + "Tutor_app/updateClass.php";

    EditText edtDes, edtAdd, edtSub, edtGrade, edtFee, edtTimes, edtReq;
    Button btnEdit, btnCancel;
    ImageView imBack;
    int id_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_edit_class);

        edtDes = findViewById(R.id.edtDescription);
        edtAdd = findViewById(R.id.edtAddress);
        edtSub = findViewById(R.id.edtSubject);
        edtGrade = findViewById(R.id.edtGrade);
        edtFee = findViewById(R.id.edtFee);
        edtTimes = findViewById(R.id.edtTimes);
        edtReq = findViewById(R.id.edtRequirement);
        btnEdit = findViewById(R.id.btnSignup);
        btnCancel = findViewById(R.id.btnCancel);
        imBack = findViewById(R.id.icBack);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_edit_class.this, Parent_Board.class));
            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_edit_class.this, Parent_Board.class));
            }
        });

        Intent intent = getIntent();
        Class classTutor = (Class) intent.getSerializableExtra("dataClass");

        edtDes.setText(classTutor.getDescription());
        edtSub.setText(classTutor.getSubject());
        edtGrade.setText(classTutor.getGrade());
        edtFee.setText(classTutor.getFee());
        edtReq.setText(classTutor.getRequirement());
        edtAdd.setText(classTutor.getAddress());
        edtTimes.setText(classTutor.getTimes());
        id_class = classTutor.getId_class();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateClass(urlUpdateClass);
            }
        });
    }
    public void UpdateClass(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Update successfully")){
                            Toast.makeText(Parent_edit_class.this, "Update class successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Parent_edit_class.this, Parent_Board.class));
                            finish();
                        } else {
                            Toast.makeText(Parent_edit_class.this, "Fail to update class", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_edit_class.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_class", String.valueOf(id_class));
                param.put("cr_date", LocalDate.now().toString());
                param.put("description", edtDes.getText().toString().trim());
                param.put("Address", edtAdd.getText().toString().trim());
                param.put("subject", edtSub.getText().toString().trim());
                param.put("grade", edtGrade.getText().toString().trim());
                param.put("fee", edtFee.getText().toString().trim());
                param.put("number", edtTimes.getText().toString().trim() + "/week");
                param.put("requirement", edtReq.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}