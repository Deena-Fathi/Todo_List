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

public class AddTodoViewModel extends AndroidViewModel {

    @NonNull
    private final TodosRepository repository;

    @NonNull
    private final MutableLiveData<Date> date = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<Boolean> todoSaved = new MutableLiveData<>();

    public AddTodoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TodosRepository(application);
    }

    @NonNull
    public LiveData<Date> getDate() {
        return date;
    }

    public LiveData<Boolean> todoSaved() {
        return todoSaved;
    }

    @NonNull
    private Calendar getCalendar() {
        final Calendar calendar = Calendar.getInstance();
        final Date currentDate = date.getValue();
        calendar.setTime(currentDate == null ? new Date() : currentDate);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void onTimePickerSet(int hourOfDay, int minute) {
        final Calendar calendar = getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        date.setValue(calendar.getTime());
    }

    public void onDatePickerSet(int year, int month, int dayOfMonth) {
        final Calendar calendar = getCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.setValue(calendar.getTime());
    }

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
                () -> todoSaved.setValue(true)  // Notify the activity when we're done saving.
        );
    }

    static class InvalidInputException extends Exception {
        @NonNull
        private final String message;

        InvalidInputException(@NonNull String message) {
            this.message = message;
        }

        @Override
        @NonNull
        public String getMessage() {
            return message;
        }
    }
}
