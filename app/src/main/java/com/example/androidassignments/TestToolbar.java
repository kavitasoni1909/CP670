package com.example.androidassignments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TestToolbar extends AppCompatActivity {

    public Snackbar customSnackBar;
    private String snackMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        snackMessage = new String();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getResources().getString(R.string.toolbarButon), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true ;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()){
            case R.id.action_one:
                Log.d("ToolBar","Event icon was selected");
                if(snackMessage.isEmpty()) {
                    snackMessage = getResources().getString(R.string.snackNotification).toString();
                }
                customSnackBar = Snackbar.make(findViewById(R.id.CoordinatorLayout), snackMessage, Snackbar.LENGTH_LONG);
                customSnackBar.show();
                break;
            case R.id.action_two:
                Log.d("ToolBar","Go Back icon was selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.alertDialogTitle);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Cancel is clicked",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();


                break;
            case R.id.action_three:
                Log.d("ToolBar","Comment icon was selected");
                final AlertDialog.Builder snackBuilder = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = this.getLayoutInflater();
                final View mView = inflater.inflate(R.layout.cusom_layout, null);
                snackBuilder.setView(mView);
                final EditText newSnackBarText =  mView.findViewById(R.id.customMessage);
                snackBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if(!newSnackBarText.getText().toString().isEmpty())
                                snackMessage = newSnackBarText.getText().toString();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                snackBuilder.create();
                snackBuilder.show();
                break;
            case R.id.action_four:
                Log.d("ToolBar","Settings icon was selected");
                Toast.makeText(this,"Version 1.0 Kavita Soni", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
