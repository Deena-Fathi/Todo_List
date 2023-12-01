package com.example.to_dolist.ui.add.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.Date;

public class AddTodoViewModel extends AndroidViewModel {

    @NonNull
    private final MutableLiveData<Date> date = new MutableLiveData<>();

    public AddTodoViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<Date> getDate() {
        return date;
    }

    public void onTimePickerSet(int hourOfDay, int minute) {
        final Calendar calendar = Calendar.getInstance();
        final Date currentDate = date.getValue();
        calendar.setTime(currentDate == null ? new Date() : currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        date.setValue(calendar.getTime());
    }

    public void onDatePickerSet(int year, int month, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        final Date currentDate = date.getValue();
        calendar.setTime(currentDate == null ? new Date() : currentDate);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.setValue(calendar.getTime());
    }

    public void onSave(@NonNull String todo) throws Exception {
        // TODO: implement `onSave`
        throw new Exception("Save todo not yet implemented");
    }
}
