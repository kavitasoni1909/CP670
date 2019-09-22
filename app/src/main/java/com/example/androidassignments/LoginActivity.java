package com.example.androidassignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Login Activity";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EditText login;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText) findViewById(R.id.loginEditText);
        loginButton = findViewById(R.id.loginButton);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String text = sharedPreferences.getString("LoginEmail", "email@domain.com");
        login.setText(text);
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In on Resume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In on Start");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In on Pause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In on Stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In on Destroy");
        super.onDestroy();
    }

    public void onLogin(View view) {
        editor = sharedPreferences.edit();
        editor.putString("LoginEmail", login.getText().toString());
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
