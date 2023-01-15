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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parent_Home extends AppCompatActivity {

    BottomNavigationView nav;

    String urlGetData_Tutor = Constants.BASE_URL + "Tutor_app/getTutor.php";

    TextView txtFullName;
    ListView tutorList;
    ArrayList<Tutor> arrayTutor;
    TutorAdapter adapterTutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        nav = (BottomNavigationView) findViewById(R.id.Btm_Navigator);
        txtFullName = findViewById(R.id.txtFullName);

        txtFullName.setText(Constants.FULLNAME);

        nav.setSelectedItemId(R.id.navigation_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        return true;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(Parent_Home.this, Parent_Board.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_chat:
                        startActivity(new Intent(Parent_Home.this, Parent_Chat.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Parent_Home.this, Parent_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        tutorList = (ListView) findViewById(R.id.listviewTutor);
        arrayTutor = new ArrayList<>();
        adapterTutor = new TutorAdapter(this, R.layout.line_tutor, arrayTutor);
        tutorList.setAdapter(adapterTutor);
        getData(urlGetData_Tutor);
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
                                arrayTutor.add(new Tutor(
                                        object.getString("Fullname"),
                                        object.getString("Email"),
                                        object.getString("Password"),
                                        object.getString("Phonenumber"),
                                        object.getString("Address"),
                                        object.getString("School"),
                                        object.getString("Gender"),
                                        object.getString("YoB"),
                                        object.getString("Subject"),
                                        object.getString("Experience")
                                ));

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        adapterTutor.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Parent_Home.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}