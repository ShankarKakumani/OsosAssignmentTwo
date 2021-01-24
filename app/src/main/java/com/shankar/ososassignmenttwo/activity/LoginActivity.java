package com.shankar.ososassignmenttwo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.shankar.customtoast.Keyboard;
import com.shankar.ososassignmenttwo.R;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText usernameET, passwordET;
    Button submitButton;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLoginStatus();


        submitButton= findViewById(R.id.submit_button);
        usernameET= findViewById(R.id.usernameET);
        passwordET= findViewById(R.id.passwordET);
        submitButton.setOnClickListener(v -> startLogin());

    }

    private void startLogin() {
        String usernameST = Objects.requireNonNull(usernameET.getText()).toString();
        String passwordST = Objects.requireNonNull(passwordET.getText()).toString();
        Keyboard.hideKeyboard(this);


        if(usernameST.isEmpty())
        {
            usernameET.setError("Empty!");
            usernameET.requestFocus();
        }
        else if(passwordST.isEmpty())
        {
            passwordET.setError("Empty!");
            passwordET.requestFocus();
        }
        else if(!usernameST.equals("123"))
        {
            usernameET.setError("Invalid!");
            usernameET.requestFocus();
        }
        else if(!passwordST.equals("123"))
        {
            passwordET.setError("Invalid!");
            passwordET.requestFocus();
        }

        else
        {
            editor = preferences.edit();
            editor.putString("status", "logged_in");
            editor.apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    private void userLoginStatus() {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String status = preferences.getString("status", null);

        if (status != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}