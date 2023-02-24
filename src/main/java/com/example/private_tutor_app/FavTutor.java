package com.example.private_tutor_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.private_tutor_app.adapter.TutorAdapter;
import com.example.private_tutor_app.model.Tutor;
import com.example.private_tutor_app.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavTutor extends AppCompatActivity {
    TextView txt;
    ImageView back;
    ListView tutorList;
    TutorAdapter tutorAdapter;
    ArrayList<Tutor> tutorArrayList;

    String url_getFavTutor = Constants.BASE_URL + "Tutor_app/favTutorDis.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_tutor);

        txt = findViewById(R.id.text);
        back = findViewById(R.id.icBack);
        tutorList = findViewById(R.id.listfavTutor);
        tutorArrayList = new ArrayList<>();
        tutorAdapter = new TutorAdapter(this, R.layout.line_tutor, tutorArrayList);
        tutorList.setAdapter(tutorAdapter);

        GetData(url_getFavTutor);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavTutor.this, Parent_Profile.class));
            }
        });
    }
    public void GetData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DisplayTutor(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FavTutor.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_parent", String.valueOf(Constants.ID_PARENT));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DisplayTutor(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("FavTutors");

            tutorArrayList.clear();
            int count =0;
            for(int i=0; i< result.length(); i++){
                try {
                    JSONObject object = result.getJSONObject(i);
                    tutorArrayList.add(new Tutor(
                            object.getInt("Id_Tutor"),
                            object.getString("Fullname"),
                            object.getString("Email"),
                            object.getString("Password"),
                            object.optString("Id_User"),
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
                    Toast.makeText(FavTutor.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                tutorAdapter.notifyDataSetChanged();
            }
            if(count == 0){
                txt.setText("There's been no favourite tutor added");
                txt.setTextColor(Color.parseColor("#989797"));
            } else {
                txt.setText("My favourite tutors");
                txt.setTextColor(Color.parseColor("#4CAF50"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}