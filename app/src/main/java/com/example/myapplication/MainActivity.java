package com.example.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //load xml file
    }

    public void change_perm(View view) {
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)); //onclick function get notification access
    }
    public void start_silentservice(View view) {
        startService(new Intent(this,MyService.class)); //start service
    }

    public void stop_silentservice(View view) {
        stopService(new Intent(this,MyService.class)); //stop service
    }
}
