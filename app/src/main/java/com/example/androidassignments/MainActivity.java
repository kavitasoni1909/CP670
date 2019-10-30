package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "Main Activity";

    private Button button;
    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        chatButton = findViewById(R.id.chatButton);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned to Main Activity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(MainActivity.this, messagePassed, duration);
            toast.show();
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, 10);
    }

    public void onChatButtonClick(View view){
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent intent = new Intent(MainActivity.this, ChatWindow.class);
        startActivity(intent);
    }

    public void onTestToolBarButtonClick(View view){
        Log.i(ACTIVITY_NAME, "User clicked Test ToolBar Button");
        Intent intent = new Intent(MainActivity.this, TestToolbar.class);
        startActivity(intent);
    }
}
