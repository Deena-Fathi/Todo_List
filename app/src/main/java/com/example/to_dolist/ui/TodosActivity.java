package com.example.to_dolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.to_dolist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TodosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        final RecyclerView todosRecycler = findViewById(R.id.recycler_todos);
        final TodoListAdapter adapter = new TodoListAdapter();
        todosRecycler.setAdapter(adapter);
        todosRecycler.setLayoutManager(new LinearLayoutManager(this));

        final FloatingActionButton addTodoButton = findViewById(R.id.button_add_todo);
        addTodoButton.setOnClickListener(v -> navigateToAddTodoActivity());
    }

    private void navigateToAddTodoActivity() {
        startActivity(new Intent(this, AddTodoActivity.class));
    }
}