package com.example.to_dolist.ui.todo.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.ui.add.todo.AddTodoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        final TodoListViewModel viewModel = new ViewModelProvider(this)
                .get(TodoListViewModel.class);

        final RecyclerView todosRecycler = findViewById(R.id.recycler_todos);
        final TodoListAdapter todosAdapter = new TodoListAdapter();
        todosRecycler.setAdapter(todosAdapter);

        // Update the recycler view whenever the todos change.
        viewModel.getTodos().observe(this, todosAdapter::submitList);

        final FloatingActionButton addTodoButton = findViewById(R.id.button_add_todo);
        addTodoButton.setOnClickListener(this::onNavigateAddTodo);
    }

    private void onNavigateAddTodo(View v) {
        startActivity(new Intent(this, AddTodoActivity.class));
    }
}