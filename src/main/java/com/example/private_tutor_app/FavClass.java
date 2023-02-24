package com.example.private_tutor_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.private_tutor_app.adapter.TutorAdapter;
import com.example.private_tutor_app.model.Tutor;
import com.example.private_tutor_app.utilities.Constants;

import java.util.ArrayList;

public class FavClass extends AppCompatActivity {

    TextView txt;
    ImageView back;
    ListView classList;
    TutorAdapter classAdapter;
    ArrayList<Class> classArrayList;

    String url_getFavClass = Constants.BASE_URL + "Tutor_app/getFavClass.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_class);
    }
}