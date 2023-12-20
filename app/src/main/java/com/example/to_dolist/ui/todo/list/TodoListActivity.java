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

/**
 * Activity for displaying the list of todos.
 * Allows users to view the list of todos and mark them as done. <br>
 *
 * - viewModel         ViewModel for managing data and business logic. <br>
 * - todosAdapter      Adapter for displaying the list of todos. <br>
 * - todosDoneAdapter  Adapter for displaying the list of done todos. <br>
 * - todosRecycler     RecyclerView for displaying the list of todos and done todos. <br>
 * - addTodoButton     FloatingActionButton for adding a new to-do.
 */
public class TodoListActivity extends AppCompatActivity {

    private TodoListViewModel viewModel;

    private TodoListAdapter todosAdapter;

    private TodoListAdapter todosDoneAdapter;

    private RecyclerView todosRecycler;

    private FloatingActionButton addTodoButton;

    /**
     * Called when the activity is created.
     * Initializes the UI components, ViewModel, and sets up event listeners.
     *
     * @param savedInstanceState Saved state of the activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        todosAdapter = new TodoListAdapter(viewModel::onTodoCheckedChange);
        todosDoneAdapter = new TodoListAdapter(viewModel::onTodoCheckedChange);
        todosRecycler = findViewById(R.id.recycler_todos);
        addTodoButton = findViewById(R.id.button_add_todo);

        // Show the list of todos with a header "Todos" and below it
        // the list of done todos with a header "Done".
        todosRecycler.setAdapter(
                new ConcatAdapter(
                        new TodoListHeaderAdapter("Todos"),
                        todosAdapter,
                        new TodoListHeaderAdapter("Done"),
                        todosDoneAdapter
                )
        );

        // Update the list of todos when the data changes.
        viewModel.getTodos().observe(
                this,
                todos -> todosAdapter.submitList(todos)
        );
        viewModel.getTodosDone().observe(
                this,
                todos -> todosDoneAdapter.submitList(todos)
        );
        // Set up event listener for the FloatingActionButton
        addTodoButton.setOnClickListener(
                v -> startActivity(new Intent(this, AddTodoActivity.class))
        );
    }
}