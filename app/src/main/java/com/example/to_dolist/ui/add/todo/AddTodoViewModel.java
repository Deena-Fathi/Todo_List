package com.example.to_dolist.ui.add.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.to_dolist.R;
import com.example.to_dolist.database.Todo;
import com.example.to_dolist.database.TodosRepository;

import java.util.Calendar;
import java.util.Date;

/**
 * ViewModel for the AddTodoActivity.
 * Manages the interaction between the UI and the underlying data repository. <br>
 *
 * - repository        Repository for handling data operations. <br>
 * - date              LiveData representing the selected date for the to-do. <br>
 * - todoSaved         LiveData indicating whether the to-do is successfully saved. <br>
 * - getCalendar       Retrieves a Calendar instance based on the current date or selected date. <br>
 * - onTimePickerSet   Updates the selected date when the time is picked. <br>
 * - onDatePickerSet   Updates the selected date when the date is picked. <br>
 * - onSave            Handles the save operation for a new to-do, validating input and triggering save in the repository. <br>
 * - InvalidInputException Custom exception class for handling invalid input errors.
 */
public class AddTodoViewModel extends AndroidViewModel {

    @NonNull
    private final TodosRepository repository;

    @NonNull
    private final MutableLiveData<Date> date = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<Boolean> todoSaved = new MutableLiveData<>();

    /**
     * Constructor for the AddTodoViewModel.
     *
     * @param application Application instance for accessing resources and the application context.
     */
    public AddTodoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TodosRepository(application);
    }

    /**
     * Getter for the LiveData representing the selected date for the to-do.
     *
     * @return LiveData containing the selected date.
     */
    @NonNull
    public LiveData<Date> getDate() {
        return date;
    }

    /**
     * Getter for the LiveData indicating whether the to-do is successfully saved.
     *
     * @return LiveData indicating the success of the save operation.
     */
    public LiveData<Boolean> todoSaved() {
        return todoSaved;
    }

    /**
     * Retrieves a Calendar instance based on the current date or selected date.
     *
     * @return Calendar instance representing the current or selected date.
     */
    @NonNull
    private Calendar getCalendar() {
        final Calendar calendar = Calendar.getInstance();
        final Date currentDate = date.getValue();
        calendar.setTime(currentDate == null ? new Date() : currentDate);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    /**
     * Updates the selected date when the time is picked.
     *
     * @param hourOfDay Selected hour.
     * @param minute    Selected minute.
     */
    public void onTimePickerSet(int hourOfDay, int minute) {
        final Calendar calendar = getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        date.setValue(calendar.getTime());
    }

    /**
     * Updates the selected date when the date is picked.
     *
     * @param year       Selected year.
     * @param month      Selected month.
     * @param dayOfMonth Selected day of the month.
     */
    public void onDatePickerSet(int year, int month, int dayOfMonth) {
        final Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.setValue(calendar.getTime());
    }

    /**
     * Handles the save operation for a new to-do, validating input and triggering save in the repository.
     *
     * @param todo Description or name of the to-do.
     * @throws InvalidInputException Exception thrown when the input is invalid.
     */
    public void onSave(@NonNull String todo) throws Exception {
        if (todo.isEmpty()) {
            final String error = getApplication().getString(R.string.add_todo_error_no_todo);
            throw new InvalidInputException(error);
        }

        final Date date = this.date.getValue();
        if (date == null) {
            final String error = getApplication().getString(R.string.add_todo_error_no_date);
            throw new InvalidInputException(error);
        }

        repository.insertTodo(
                new Todo(0, todo, date, false),
                () -> todoSaved.postValue(true)
        );
    }

    /**
     * Custom exception class for handling invalid input errors.
     */
    static class InvalidInputException extends Exception {
        @NonNull
        private final String message;

        /**
         * Constructor for the exception class.
         *
         * @param message Error message describing the validation failure.
         */
        InvalidInputException(@NonNull String message) {
            this.message = message;
        }

        @Override
        @NonNull
        /**
         * Getter for the error message.
         *
         * @return Error message.
         */
        public String getMessage() {
            return message;
        }
    }
}
