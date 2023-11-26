package com.example.to_dolist.ui.add.todo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

public class TodoTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AddTodoViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentActivity activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(AddTodoViewModel.class);

        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(activity);
        return new TimePickerDialog(activity, this, hour, minute, is24HourFormat);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        viewModel.onTimePickerSet(hourOfDay, minute);
    }
}
