package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parent_Board extends AppCompatActivity {

    BottomNavigationView nav;
    LinearLayout layCreate;
    TextView txtMyClass;

    String urlGetData_class = Constants.BASE_URL + "Tutor_app/getClass.php";
    String urlDelete = Constants.BASE_URL + "Tutor_app/deleteClass.php";

    ListView classList;
    ArrayList<Class> arrayClasses;
    ClassCreatedAdapter adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_board);
        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        nav.setSelectedItemId(R.id.navigation_home);

        txtMyClass = findViewById(R.id.txtMyClass);
        layCreate = findViewById(R.id.layCreateNewClass);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        startActivity(new Intent(Parent_Board.this, Parent_Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_dashboard:
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(Parent_Board.this, Parent_Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Parent_Board.this, Parent_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        layCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Parent_Board.this, Parent_create_class.class));
            }
        });

        classList = (ListView) findViewById(R.id.listCreatedClass);
        arrayClasses = new ArrayList<>();
        adapterClass = new ClassCreatedAdapter(this, R.layout.line_created_class, arrayClasses);
        classList.setAdapter(adapterClass);
        getCreatedClass(urlGetData_class);
    }
    private void getCreatedClass(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        arrayClasses.clear();
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id_parent = object.getInt("Id_Parent");
                                if(id_parent == Constants.ID_PARENT){
                                    arrayClasses.add(new Class(
                                            object.getString("Description"),
                                            object.getString("Create_date"),
                                            object.getString("Subject"),
                                            object.getString("Grade"),
                                            object.getString("Fee"),
                                            object.getString("Address"),
                                            object.getString("Require"),
                                            object.getString("Times")
                                    ));
                                    count++;
                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        if(count==0){
                            txtMyClass.setText("There's no created class");
                            txtMyClass.setTextColor(Color.parseColor("#989797"));
                        }
                        adapterClass.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_Board.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public void DeleteClass(int idClass){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("Delete successfully")){
                            Toast.makeText(Parent_Board.this, "Delete class successfully", Toast.LENGTH_SHORT).show();
                            getCreatedClass(urlGetData_class);
                        } else Toast.makeText(Parent_Board.this, "Fail to delete class", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_Board.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_class", String.valueOf(idClass));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}