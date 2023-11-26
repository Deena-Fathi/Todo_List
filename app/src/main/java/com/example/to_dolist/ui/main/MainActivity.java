package com.example.to_dolist.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.to_dolist.R;
import com.example.to_dolist.ui.todo.list.TodoListActivity;

public class MainActivity extends AppCompatActivity {

    private static final String FIRST_LAUNCH_KEY = "first_launch";

    private Button getStartedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        // If the app was launched before, skip the welcome screen.
        final boolean firstLaunch = getPreferences(Context.MODE_PRIVATE).getBoolean(FIRST_LAUNCH_KEY, true);
        if (!firstLaunch) {
            replaceWithTodoList();
            return;
        }

        setContentView(R.layout.activity_main);
        getStartedButton = findViewById(R.id.button_get_started);
        getStartedButton.setOnClickListener(this::onNavigateTodoList);
    }

    private void replaceWithTodoList() {
        startActivity(new Intent(this, TodoListActivity.class));
        this.finish();
    }

    private void onNavigateTodoList(View v) {
        getPreferences(Context.MODE_PRIVATE).edit().putBoolean(FIRST_LAUNCH_KEY, false).apply();
        replaceWithTodoList();
    }
}
