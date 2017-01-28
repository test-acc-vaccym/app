package com.gitlab.PCU.PCU;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class UnlockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException ignored) {
        }
        /*Intent service = new Intent(getApplicationContext(), BackgroundService.class);
        startService(service);*/
    }
}