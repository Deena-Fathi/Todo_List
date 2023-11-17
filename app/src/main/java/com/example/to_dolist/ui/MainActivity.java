package com.example.to_dolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.to_dolist.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button getStartedButton = findViewById(R.id.button_get_started);
        getStartedButton.setOnClickListener(v -> replaceWithTodosActivity());
    }

    private void replaceWithTodosActivity() {
        startActivity(new Intent(this, TodosActivity.class));
        this.finish();
    }
}
