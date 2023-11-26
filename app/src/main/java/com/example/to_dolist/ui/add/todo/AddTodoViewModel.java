package com.example.to_dolist.ui.add.todo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.function.Consumer;

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

    public void onTimePickerSet(int hour, int minute) {
        // TODO: update `date`
        Log.d("AddTodoViewModel", "onTimePickerSet: " + hour + ", " + minute);
    }

    public void onDatePickerSet(int year, int month, int day) {
        // TODO: update `date`
        Log.d("AddTodoViewModel", "onDatePickerSet: " + year + ", " + month + ", " + day);
    }

    public void onSave(@NonNull String todo, Consumer<String> onError, Runnable onSuccess) {
        // TODO: implement `onSave`
        onError.accept("Save todo not yet implemented");
    }
}
