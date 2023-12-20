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

/**
 * Fragment for displaying the time picker dialog.
 * Handles the time selection and updates the ViewModel accordingly. <br>
 *
 * - viewModel ViewModel for managing data and business logic. <br>
 * - onCreateDialog onCreateDialog Creates the time picker dialog with the current time as the default. <br>
 * - onTimeSet      Handles the time selection and updates the ViewModel accordingly.
 */
public class TodoTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AddTodoViewModel viewModel;

    @NonNull
    @Override
    /**
     * Called to create the dialog for picking a time.
     *
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a freshly created Fragment.
     * @return Dialog for picking a time with the current time as the default.
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final FragmentActivity activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(AddTodoViewModel.class);

        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(activity);
        return new TimePickerDialog(activity, this, hour, minute, is24HourFormat);
    }

    /**
     * Called when a time is set in the time picker.
     *
     * @param view        The time picker view.
     * @param hourOfDay   The selected hour.
     * @param minute      The selected minute.
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        viewModel.onTimePickerSet(hourOfDay, minute);
    }
}
