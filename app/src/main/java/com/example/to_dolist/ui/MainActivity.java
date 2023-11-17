package com.example.to_dolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.to_dolist.R;

public class MainActivity extends AppCompatActivity {

    private static final String FIRST_LAUNCH_KEY = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences activityPreferences = getPreferences(Context.MODE_PRIVATE);
        final boolean firstLaunch = activityPreferences.getBoolean(FIRST_LAUNCH_KEY, true);
        if (!firstLaunch) {
            replaceWithTodosActivity();
            return;
        }

        final Button getStartedButton = findViewById(R.id.button_get_started);
        getStartedButton.setOnClickListener(v -> {
            activityPreferences.edit().putBoolean(FIRST_LAUNCH_KEY, false).apply();
            replaceWithTodosActivity();
        });
    }

    private void replaceWithTodosActivity() {
        startActivity(new Intent(this, TodosActivity.class));
        this.finish();
    }
}
