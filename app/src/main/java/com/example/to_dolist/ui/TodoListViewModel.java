package com.example.to_dolist.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_dolist.database.Todo;
import com.example.to_dolist.database.TodosDao;
import com.example.to_dolist.database.TodosDatabase;

import java.util.List;

public class TodoListViewModel extends AndroidViewModel {

    @NonNull
    private final TodosDao dao;

    private final LiveData<List<Todo>> todos;

    public TodoListViewModel(Application application) {
        super(application);
        final TodosDatabase db = TodosDatabase.getDatabase(application);
        this.dao = db.dao();
        this.todos = dao.getTodos();
    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void onInsertTodo(Todo todo) {
        TodosDatabase.databaseWriteExecutor.execute(() -> dao.insertTodo(todo));
    }

    public void onUpdateTodo(Todo todo) {
        TodosDatabase.databaseWriteExecutor.execute(() -> dao.updateTodo(todo));
    }
}
