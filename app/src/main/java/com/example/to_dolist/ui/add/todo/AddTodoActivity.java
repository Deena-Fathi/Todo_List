package com.example.to_dolist.ui.add.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_dolist.R;
import com.example.to_dolist.notification.AlarmReceiver;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    private final DateFormat timeFormat = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);

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
        viewModel.todoSaved().observe(this, this::onTodoSavedChanged);
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
        try {
            viewModel.onSave(todoInput.getText().toString());
        } catch (AddTodoViewModel.InvalidInputException e) {
            onSaveError(e.getMessage());
        } catch (Exception e) {
            onSaveError(getString(R.string.add_todo_error_unexpected));
        }
    }

    private void onSaveError(@NonNull String error) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG).show();
    }

    private void onTodoSavedChanged(boolean saved) {
        final Date date = viewModel.getDate().getValue();
        if (!saved || date == null) {
            return;
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);

        this.finish();
    }
}
