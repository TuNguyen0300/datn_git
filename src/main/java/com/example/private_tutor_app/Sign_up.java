package com.example.private_tutor_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity implements View.OnClickListener{
    EditText edtFullname, edtEmail, edtPassword;
    RadioGroup rd_group;
    RadioButton rd_parent, rd_tutor;
    Button btn_signup;
    TextView rt_signin;
    ProgressBar progressBar;
    ProgressDialog dialog;

    FirebaseAuth mAuth;
    DatabaseReference reference;

    String urlRegister = Constants.BASE_URL + "Tutor_app/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFullname = findViewById(R.id.fullname);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btn_signup = findViewById(R.id.btnSignup);
        rt_signin = findViewById(R.id.return_signin);
        rd_group = findViewById(R.id.radio_group);
        rd_parent = findViewById(R.id.radio_parent);
        rd_tutor = findViewById(R.id.radio_tutor);
        progressBar = findViewById(R.id.progressBar);

        rt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, Sign_in.class);
                startActivity(intent);
                finish();
            }
        });

        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_parent:
                        break;
                    case R.id.radio_tutor:
                        break;
                }
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading...");
        dialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        UserRegister();
    }

    public void UserRegister(){
        String fullname = edtFullname.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String role = "";
        if(rd_parent.isChecked()){
            role = "parent";
        } else if(rd_tutor.isChecked()){
            role = "tutor";
        }
        if (fullname.isEmpty()) {
            edtFullname.setError("Fullname is required");
            edtFullname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6 && password.length() > 50){
            edtPassword.setError("Password length should be 6-50 characters");
            edtPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email");
            edtEmail.requestFocus();
            return;
        }
        if (!rd_parent.isChecked() && !rd_tutor.isChecked()){
            Toast.makeText(this, "You must choose your role as a tutor/parent", Toast.LENGTH_SHORT).show();
        }
        if( !fullname.equals(null) && !email.equals(null) && !password.equals(null) && (rd_parent.isChecked() || rd_tutor.isChecked())){
            InsertUserFire(role);
        }

    }

    public void InsertUserFire(String role){
        String fullname = edtFullname.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                            HashMap<Object, String> map = new HashMap<>();
                            map.put("id", userID);
                            map.put("fullname", fullname);
                            map.put("email", email);
                            map.put("password", password);
                            map.put("role", role);
                            map.put("imageUrl", "default");

                            InsertUserSQL(urlRegister, role, userID);

                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(Sign_up.this, Sign_in.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Toast.makeText(Sign_up.this, "Register successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                            });
                        }
                         else {
                            Toast.makeText(Sign_up.this, "Fail to register", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }}

                });
    }
    public void InsertUserSQL(String url, String role, String id_user){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success create")){
                        } else {
                            Toast.makeText(Sign_up.this, "Fail to sign up" + response.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sign_up.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("fullname", edtFullname.getText().toString().trim());
                param.put("email", edtEmail.getText().toString().trim());
                param.put("password", edtPassword.getText().toString().trim());
                param.put("role", role);
                param.put("id_user", id_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}