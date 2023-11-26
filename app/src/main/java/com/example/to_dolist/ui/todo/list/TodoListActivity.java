package com.example.to_dolist.ui.todo.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.database.Todo;
import com.example.to_dolist.ui.add.todo.AddTodoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private TodoListViewModel viewModel;

    private TodoListAdapter todosAdapter;

    private RecyclerView todosRecycler;

    private FloatingActionButton addTodoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        todosAdapter = new TodoListAdapter();
        todosRecycler = findViewById(R.id.recycler_todos);
        addTodoButton = findViewById(R.id.button_add_todo);

        todosRecycler.setAdapter(todosAdapter);
        viewModel.getTodos().observe(this, this::onTodosChanged);
        addTodoButton.setOnClickListener(this::onNavigateAddTodo);
    }

    private void onTodosChanged(List<Todo> todos) {
        // Update the recycler view with the new list.
        todosAdapter.submitList(todos);
    }

    private void onNavigateAddTodo(View v) {
        startActivity(new Intent(this, AddTodoActivity.class));
    }
}