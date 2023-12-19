package com.example.to_dolist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodosRepository {

    @NonNull
    private final TodosDao dao;

    @NonNull
    private final LiveData<List<Todo>> todos;

    public TodosRepository(@NonNull Context context) {
        final TodosDatabase db = TodosDatabase.getDatabase(context);
        this.dao = db.dao();
        this.todos = dao.getTodos();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertTodo(@NonNull Todo todo, Runnable onComplete) {
        TodosDatabase.databaseWriteExecutor.execute(() -> {
            dao.insertTodo(todo);
            onComplete.run();
        });
    }

    @NonNull
    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    public void updateTodo(@NonNull Todo todo) {
        TodosDatabase.databaseWriteExecutor.execute(() -> dao.updateTodo(todo));
    }

    public void deleteTodoById(int id, Runnable onComplete) {
        TodosDatabase.databaseWriteExecutor.execute(() -> {
            dao.deleteTodoById(id);
            onComplete.run();
        });
    }

    public void deleteAllTodos(Runnable onComplete) {
        TodosDatabase.databaseWriteExecutor.execute(() -> {
            dao.deleteAllTodos();
            onComplete.run();
        });
    }
}
