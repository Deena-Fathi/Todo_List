package com.example.to_dolist.ui.add.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_dolist.R;
import com.google.android.material.snackbar.Snackbar;

public class AddTodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        final AddTodoViewModel viewModel = new ViewModelProvider(this)
                .get(AddTodoViewModel.class);

        final Button datePickerButton = findViewById(R.id.button_todo_date_picker);
        datePickerButton.setOnClickListener(this::onShowDatePicker);

        final Button timePickerButton = findViewById(R.id.button_todo_time_picker);
        timePickerButton.setOnClickListener(this::onShowTimePicker);

        final TextView dateText = findViewById(R.id.text_todo_date);
        final TextView timeText = findViewById(R.id.text_todo_time);
        viewModel.getDate().observe(this, date -> {
            // TODO: update `dateText` and `timeText`
            Log.d("AddTodoActivity", "date: " + date);
        });

        final EditText todoInput = findViewById(R.id.input_todo);
        final Button saveButton = findViewById(R.id.button_save_todo);
        saveButton.setOnClickListener(v -> {
            final String todo = todoInput.getText().toString();
            viewModel.onSave(todo, this::onSaveError, this::onSaveSuccess);
        });
    }

    private void onShowDatePicker(View v) {
        final TodoDatePickerFragment datePicker = new TodoDatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "TodoDatePicker");
    }

    private void onShowTimePicker(View v) {
        final TodoTimePickerFragment timePicker = new TodoTimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "TodoTimePicker");
    }

    private void onSaveError(@NonNull String error) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
    }

    private void onSaveSuccess() {
        this.finish();
    }
}
