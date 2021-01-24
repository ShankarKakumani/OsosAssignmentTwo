package com.shankar.ososassignmenttwo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.shankar.customtoast.Keyboard;
import com.shankar.customtoast.StatusBar;
import com.shankar.customtoast.Toasty;
import com.shankar.ososassignmenttwo.R;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText usernameET, passwordET;
    SharedPreferences preferences;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        usernameET= findViewById(R.id.usernameET);
        passwordET= findViewById(R.id.passwordET);
        findViewById(R.id.submit_button).setOnClickListener(v -> startLogin());

        StatusBar.setStatusBarColorWhite(this);
        userLoginStatus();
    }

    private void startLogin() {
        String usernameST = Objects.requireNonNull(usernameET.getText()).toString();
        String passwordST = Objects.requireNonNull(passwordET.getText()).toString();
        Keyboard.hideKeyboard(this);


        if(!usernameST.equals("123"))
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
            loginViewModel.updatePreference(preferences);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


    }


    private void userLoginStatus() {

        if(loginViewModel.isLogin(preferences))
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}