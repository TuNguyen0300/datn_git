package com.example.private_tutor_app.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.private_tutor_app.Constants;

public class PreferenceManager {
    private final SharedPreferences sharedPreferences;


    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME,context.MODE_PRIVATE);
    }
    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }
    public void putString(String key, String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }
    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }
    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
