package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.adapter.ClassAdapter;
import com.example.private_tutor_app.adapter.TutorAdapter;
import com.example.private_tutor_app.model.Class;
import com.example.private_tutor_app.model.Tutor;
import com.example.private_tutor_app.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends AppCompatActivity {

    EditText keySearch;
    ImageView btnSearch, btnBack;
    TextView txtSearchResult;

    ListView listResults;
    ArrayList<Tutor> arrayTutor;
    ArrayList<Class> arrayClasses;
    TutorAdapter adapterTutor;
    ClassAdapter adapterClass;

    Intent intent;
    String role;

    String urlSearchTutor = Constants.BASE_URL + "Tutor_app/searchTutor.php";
    String urlSearchClass = Constants.BASE_URL + "Tutor_app/searchClass.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keySearch = findViewById(R.id.boxSearch);
        btnSearch = findViewById(R.id.btnSearch);
        listResults = findViewById(R.id.listResults);
        txtSearchResult = findViewById(R.id.txtResult);
        btnBack = findViewById(R.id.imgBack);

        txtSearchResult.setVisibility(View.INVISIBLE);

        intent = getIntent();
        role = intent.getStringExtra("code");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("tutor")){
                    arrayTutor = new ArrayList<>();
                    adapterTutor = new TutorAdapter(getApplicationContext(), R.layout.line_tutor, arrayTutor);
                    listResults.setAdapter(adapterTutor);
                    GetMatchData(urlSearchTutor);
                } else {
                    arrayClasses = new ArrayList<>();
                    adapterClass = new ClassAdapter(getApplicationContext(), R.layout.line_class, arrayClasses);
                    listResults.setAdapter(adapterClass);
                    GetMatchData(urlSearchClass);
                }
                txtSearchResult.setVisibility(View.VISIBLE);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("tutor")){
                    startActivity(new Intent(Search.this, Parent_Home.class));
                } else startActivity(new Intent(Search.this, Tutor_Home.class));
            }
        });

    }

    public void GetMatchData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(role.equals("tutor")){
                            SearchTutor(response);
                        } else {
                            SearchClass(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Search.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("key", keySearch.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void SearchTutor(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("Results");

            arrayTutor.clear();
            int count =0;
            for(int i=0; i< result.length(); i++){
                try {
                    JSONObject object = result.getJSONObject(i);
                    arrayTutor.add(new Tutor(
                            object.getString("Fullname"),
                            object.getString("Email"),
                            object.getString("Password"),
                            object.getString("Photo"),
                            object.getString("Phonenumber"),
                            object.getString("Address"),
                            object.getString("School"),
                            object.getString("Gender"),
                            object.getString("YoB"),
                            object.getString("Subject"),
                            object.getString("Experience")
                    ));
                    count++;
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Search.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                adapterTutor.notifyDataSetChanged();
            }
            if(count == 0){
                txtSearchResult.setText("There's no matching result");
                txtSearchResult.setTextColor(Color.parseColor("#989797"));
            } else {
                txtSearchResult.setText("Search result");
                txtSearchResult.setTextColor(Color.parseColor("#4CAF50"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SearchClass(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("Results");

            arrayClasses.clear();
            int count =0;
            for(int i=0; i< result.length(); i++){
                try {
                    JSONObject object = result.getJSONObject(i);
                    arrayClasses.add(new Class(
                            object.getString("Description"),
                            object.getString("Create_date"),
                            object.getString("Subject"),
                            object.getString("Grade"),
                            object.getString("Fee"),
                            object.getString("Address"),
                            object.getString("Require"),
                            object.getString("Times"),
                            object.getString("Id_Parent")
                    ));
                    count++;
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Search.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                adapterClass.notifyDataSetChanged();
            }
            if(count == 0){
                txtSearchResult.setText("There's no matching result");
                txtSearchResult.setTextColor(Color.parseColor("#989797"));
            } else {
                txtSearchResult.setText("Search result");
                txtSearchResult.setTextColor(Color.parseColor("#4CAF50"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}