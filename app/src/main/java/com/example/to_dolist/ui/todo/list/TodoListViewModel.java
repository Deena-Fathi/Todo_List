package com.example.to_dolist.ui.todo.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_dolist.database.Todo;
import com.example.to_dolist.database.TodosRepository;

import java.util.List;

public class TodoListViewModel extends AndroidViewModel {

    @NonNull
    private final TodosRepository repository;

    @NonNull
    private final LiveData<List<Todo>> todos;

    public TodoListViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TodosRepository(application);
        this.todos = repository.getTodos();
    }

    @NonNull
    public LiveData<List<Todo>> getTodos() {
        return todos;
    }
}
