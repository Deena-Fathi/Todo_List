package com.example.to_dolist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
/**
 * Repository class that acts as a bridge between the ViewModel and the underlying data sources,
 * providing a clean API for data access and manipulation related to todos. <br>
 *
 * - dao         Data Access Object (DAO) for interacting with the Room database. <br>
 * - todos       LiveData representing a list of todos retrieved from the database. <br>
 * - db          Instance of the Room database used for data operations. <br>
 * - context     Application context used for creating the database instance.
 */
public class TodosRepository {

    @NonNull
    private final TodosDao dao;

    @NonNull
    private final LiveData<List<Todo>> todos;

    /**
     * Constructs a TodosRepository by initializing the DAO and LiveData.
     *
     * @param context Application context used for creating the database instance.
     */
    public TodosRepository(@NonNull Context context) {
        final TodosDatabase db = TodosDatabase.getDatabase(context);
        this.dao = db.dao();
        this.todos = dao.getTodos();
    }

    /**
     * Inserts a new todo into the database on a background thread.
     *
     * @param todo       The todo to be inserted.
     * @param onComplete A runnable to be executed upon completion of the insertion.
     */
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertTodo(@NonNull Todo todo, Runnable onComplete) {
        TodosDatabase.databaseWriteExecutor.execute(() -> {
            dao.insertTodo(todo);
            onComplete.run();
        });
    }

    /**
     * Retrieves LiveData representing a list of todos.
     *
     * @return LiveData containing a list of todos.
     */
    @NonNull
    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    /**
     * Updates a todo in the database on a background thread.
     *
     * @param todo The todo to be updated.
     */
    public void updateTodo(@NonNull Todo todo) {
        TodosDatabase.databaseWriteExecutor.execute(() -> dao.updateTodo(todo));
    }
}
