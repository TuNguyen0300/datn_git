package com.example.private_tutor_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.private_tutor_app.adapter.ClassAdapter;
import com.example.private_tutor_app.utilities.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tutor_Home extends AppCompatActivity {

    BottomNavigationView nav;
    TextView txtFullName;

    String urlGetData_class = Constants.BASE_URL + "Tutor_app/getClass.php";

    ListView classList;
    ArrayList<Class> arrayClasses;
    ClassAdapter adapterClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);

        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        txtFullName = findViewById(R.id.txtFullName);

        txtFullName.setText("Wellcome, " + Constants.FULLNAME);

        nav.setSelectedItemId(R.id.navigation_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(Tutor_Home.this, Tutor_DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(Tutor_Home.this, Tutor_Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Tutor_Home.this, Tutor_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        classList = (ListView) findViewById(R.id.listView_class);
        arrayClasses = new ArrayList<>();
        adapterClass = new ClassAdapter(this, R.layout.line_class, arrayClasses);
        classList.setAdapter(adapterClass);
        getData(urlGetData_class);
    }
    private void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
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

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        adapterClass.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Tutor_Home.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}