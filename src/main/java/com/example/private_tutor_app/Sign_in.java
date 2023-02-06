package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.utilities.Constants;
import com.example.private_tutor_app.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    TextView fgPassword, rtSignUp;
    RadioGroup rd_group;
    RadioButton rd_parent, rd_tutor;
    ProgressDialog dialog;
    String urlLoginTutor = Constants.BASE_URL + "Tutor_app/getTutor.php";
    String urlLoginParent = Constants.BASE_URL + "Tutor_app/getParent.php";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.btn_signin);
        fgPassword = findViewById(R.id.txt_forgotpassword);
        rtSignUp = findViewById(R.id.return_signup);
        rd_group = findViewById(R.id.radio_group);
        rd_parent = findViewById(R.id.radio_parent);
        rd_tutor = findViewById(R.id.radio_tutor);

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

        rtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_in.this, Sign_up.class);
                startActivity(intent);
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        preferenceManager = new PreferenceManager(this);

        btnSignIn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Email is required");
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
        if( !email.isEmpty() && !password.isEmpty() ){
            if(rd_tutor.isChecked()){
                dialog.show();
                Lo(urlLoginTutor, email, password);
            } else if (rd_parent.isChecked()){
                dialog.show();
                Lo(urlLoginParent, email, password);
            }
        }
    }

    public void Lo(String url, String email, String password){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int count = 0;
                            for(int i=0; i< response.length(); i++){
                                JSONObject object = response.getJSONObject(i);

                                String mEmail = object.getString("Email");
                                String mPassword = object.getString("Password");

                                if(email.equals(mEmail) && password.equals(mPassword)){
                                    Constants.FULLNAME = object.getString("Fullname");
                                    Constants.EMAIL = object.getString("Email");
                                    Constants.PHOTO = object.getString("Photo");

                                    if(rd_parent.isChecked()){
                                        Constants.ID_PARENT = object.getInt("Id_Parent");
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        Intent intent = new Intent(Sign_in.this, Parent_Home.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                        Toast.makeText(Sign_in.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                });
                                    } else if(rd_tutor.isChecked()){
                                        Constants.ID_TUTOR = object.getInt("Id_Tutor");
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        Intent intent = new Intent(Sign_in.this, Tutor_Home.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                        Toast.makeText(Sign_in.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                });
                                    }
                                    count++;

                                }
                            }
                            if(count == 0){
                                Toast.makeText(Sign_in.this, "Email or password is wrong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sign_in.this, error.toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
//    public void SignIn(){
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection("Users")
//                .whereEqualTo("email", edtEmail.getText().toString().trim())
//                .whereEqualTo("password", edtPassword.getText().toString().trim())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful() && task.getResult() != null
//                            && task.getResult().getDocuments().size()>0){
//                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                        preferenceManager.putBoolean("isSignedIn", true);
//                        preferenceManager.putString("id", documentSnapshot.getId());
//                        preferenceManager.putString("email", documentSnapshot.getString("email"));
//                        preferenceManager.putString("fullname", documentSnapshot.getString("fullname"));
//
//                    } else Toast.makeText(this, "Unable to login", Toast.LENGTH_SHORT).show();
//                });
//    }
}
