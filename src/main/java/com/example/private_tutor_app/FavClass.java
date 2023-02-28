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
import java.util.List;
import java.util.Map;

public class FavClass extends AppCompatActivity {

    TextView txt;
    ImageView back;
    ListView classList;
    ClassAdapter classAdapter;
    ArrayList<Class> classArrayList;

    String url_getFavClass = Constants.BASE_URL + "Tutor_app/favTutorDis.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_class);

        back = findViewById(R.id.icBack);
        txt = findViewById(R.id.text);

        classList = findViewById(R.id.listfavClass);
        classArrayList = new ArrayList<>();
        classAdapter = new ClassAdapter(this, R.layout.line_class, classArrayList);
        classList.setAdapter(classAdapter);

        GetData(url_getFavClass);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavClass.this, Tutor_Profile.class));
            }
        });
    }
    public void GetData(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DisplayClass(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FavClass.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "onErrorResponse: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id_tutor", String.valueOf(Constants.ID_TUTOR));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DisplayClass(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("FavClasses");

            classArrayList.clear();
            int count =0;
            for(int i=0; i< result.length(); i++){
                try {
                    JSONObject object = result.getJSONObject(i);
                    classArrayList.add(new Class(
                            object.getInt("Id_Class"),
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
                    Toast.makeText(FavClass.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                classAdapter.notifyDataSetChanged();
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