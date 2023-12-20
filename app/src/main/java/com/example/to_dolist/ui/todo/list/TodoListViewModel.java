package com.example.to_dolist.ui.todo.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.to_dolist.database.Todo;
import com.example.to_dolist.database.TodosRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        // Filter the list of todos to only show the ones that are not done.
        return Transformations.map(
                todos,
                todos -> todos.stream()
                        .filter(todo -> !todo.done())
                        .collect(Collectors.toList())
        );
    }

    @NonNull
    public LiveData<List<Todo>> getTodosDone() {
        // Filter the list of todos to only show the ones that are done.
        return Transformations.map(
                todos,
                todos -> todos.stream()
                        .filter(Todo::done)
                        .collect(Collectors.toList())
        );
    }

    public void onTodoCheckedChange(@NonNull Todo todo, boolean checked) {
        // Update the to-do in the database when the user marks it as done.
        final Todo newTodo = new Todo(
                todo.getId(),
                todo.getTodo(),
                todo.getDate(),
                checked
        );
        repository.updateTodo(newTodo);
    }
}
