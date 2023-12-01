package com.example.to_dolist.ui.add.todo;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_dolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    private final DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

    private AddTodoViewModel viewModel;

    private EditText todoInput;

    private Button datePickerButton;

    private TextView dateText;

    private Button timePickerButton;

    private TextView timeText;

    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        viewModel = new ViewModelProvider(this).get(AddTodoViewModel.class);
        todoInput = findViewById(R.id.input_todo);
        datePickerButton = findViewById(R.id.button_todo_date_picker);
        dateText = findViewById(R.id.text_todo_date);
        timePickerButton = findViewById(R.id.button_todo_time_picker);
        timeText = findViewById(R.id.text_todo_time);
        saveButton = findViewById(R.id.button_save_todo);

        datePickerButton.setOnClickListener(this::onShowDatePicker);
        timePickerButton.setOnClickListener(this::onShowTimePicker);
        viewModel.getDate().observe(this, this::onDateChanged);
        saveButton.setOnClickListener(this::onSave);
    }

    private void onShowDatePicker(View v) {
        final TodoDatePickerFragment datePicker = new TodoDatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "TodoDatePicker");
    }

    private void onShowTimePicker(View v) {
        final TodoTimePickerFragment timePicker = new TodoTimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "TodoTimePicker");
    }

    private void onDateChanged(Date date) {
        dateText.setText(dateFormat.format(date));
        timeText.setText(timeFormat.format(date));
    }

    private void onSave(View v) {
        final String todo = todoInput.getText().toString();
        try {
            viewModel.onSave(todo);
        } catch (Exception e) {
            String error = e.getMessage();
            if (error != null) {
                makeSnackbar(error, Snackbar.LENGTH_LONG).show();
            }
            return;
        }
        this.finish();
    }

    /**
     * @noinspection SameParameterValue
     */
    private Snackbar makeSnackbar(String message, int duration) {
        return Snackbar.make(findViewById(android.R.id.content), message, duration);
    }
}
