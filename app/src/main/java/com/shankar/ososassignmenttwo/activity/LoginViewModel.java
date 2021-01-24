package com.shankar.ososassignmenttwo.activity;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

public class LoginViewModel  extends ViewModel {


    public Boolean isLogin(SharedPreferences preferences)
    {
        String status = preferences.getString("status", null);
        return status != null;

    }

    public void updatePreference(SharedPreferences preferences)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("status", "logged_in");
        editor.apply();
    }
}
