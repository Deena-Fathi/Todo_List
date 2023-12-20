package com.example.to_dolist.ui.add.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

/**
 * Fragment for displaying the date picker dialog.
 * Handles the date selection and updates the ViewModel accordingly. <br>
 *
 * - viewModel ViewModel for managing data and business logic. <br>
 * - onCreateDialog onCreateDialog Creates the date picker dialog with the current date as the default. <br>
 * - onDateSet      Handles the date selection and updates the ViewModel accordingly.
 */
public class TodoDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private AddTodoViewModel viewModel;

    @NonNull
    @Override
    /**
     * Called to create the dialog for picking a date.
     *
     * @param savedInstanceState The last saved instance state of the Fragment, or null if this is a freshly created Fragment.
     * @return Dialog for picking a date with the current date as the default.
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final FragmentActivity activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(AddTodoViewModel.class);

        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(activity, this, year, month, day);
    }

    /**
     * Called when a date is set in the date picker.
     *
     * @param view        The date picker view.
     * @param year        The selected year.
     * @param month       The selected month.
     * @param dayOfMonth  The selected day of the month.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        viewModel.onDatePickerSet(year, month, dayOfMonth);
    }
}
