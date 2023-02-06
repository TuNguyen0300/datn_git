package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update_Parent_Infor extends AppCompatActivity {

    ImageView imgAvatar;
    EditText edtFullname, edtEmail, edtPhonenumber, edtAddress;
    Button btnUpdate, btnCancel;

    String urlGetParent = Constants.BASE_URL + "Tutor_app/getParent.php";
    String urlUpdateParent = Constants.BASE_URL + "Tutor_app/updateParent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_parent_infor);

        imgAvatar = findViewById(R.id.image);
        edtFullname = findViewById(R.id.fullname);
        edtEmail = findViewById(R.id.email);
        edtPhonenumber = findViewById(R.id.phoneNumber);
        edtAddress = findViewById(R.id.address);
        btnUpdate = findViewById(R.id.btnSignup);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Update_Parent_Infor.this, Parent_Profile.class));
                finish();
            }
        });
        getParentData(urlGetParent);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edtFullname.getText().toString();
                String email = edtEmail.getText().toString();
                String phonenumber = edtPhonenumber.getText().toString();
                String address = edtAddress.getText().toString();

                if ( !fullname.equals(null) && !email.equals(null) && !phonenumber.equals(null) && !address.equals(null)){
                    UpdateParent(urlUpdateParent);
                    startActivity(new Intent(Update_Parent_Infor.this, Parent_Profile.class));
                    finish();
                } else Toast.makeText(Update_Parent_Infor.this, "Fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getParentData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if(object.getInt("Id_Parent") == Constants.ID_PARENT){
                                    edtFullname.setText(object.getString("Fullname"));
                                    edtEmail.setText(object.getString("Email"));
                                    edtAddress.setText(object.getString("Address"));
                                    edtPhonenumber.setText(object.getString("Phonenumber"));
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
                        Toast.makeText(Update_Parent_Infor.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public void UpdateParent(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("Update successfully")){
                            Toast.makeText(Update_Parent_Infor.this, "Update parent information successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Update_Parent_Infor.this, "Fail to update parent information", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_parent", String.valueOf(Constants.ID_PARENT));
                param.put("fullname", edtFullname.getText().toString().trim());
                param.put("email", edtEmail.getText().toString().trim());
                param.put("phonenumber", edtPhonenumber.getText().toString().trim());
                param.put("address", edtAddress.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


}