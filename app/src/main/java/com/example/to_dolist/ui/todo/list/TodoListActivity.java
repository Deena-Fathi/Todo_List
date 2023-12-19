package com.example.to_dolist.ui.todo.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.ui.add.todo.AddTodoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TodoListActivity extends AppCompatActivity {

    private TodoListViewModel viewModel;

    private TodoListAdapter todosAdapter;

    private TodoListAdapter todosDoneAdapter;

    private RecyclerView todosRecycler;

    private FloatingActionButton addTodoButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        todosAdapter = new TodoListAdapter(viewModel::onTodoCheckedChange);
        todosDoneAdapter = new TodoListAdapter(viewModel::onTodoCheckedChange);
        todosRecycler = findViewById(R.id.recycler_todos);
        addTodoButton = findViewById(R.id.button_add_todo);

        todosRecycler.setAdapter(
                new ConcatAdapter(
                        new TodoListHeaderAdapter("Todos"),
                        todosAdapter,
                        new TodoListHeaderAdapter("Done"),
                        todosDoneAdapter
                )
        );

        viewModel.getTodos().observe(
                this,
                todos -> todosAdapter.submitList(todos)
        );

        viewModel.getTodosDone().observe(
                this,
                todos -> todosDoneAdapter.submitList(todos)
        );

        addTodoButton.setOnClickListener(
                v -> startActivity(new Intent(this, AddTodoActivity.class))
        );
    }
}