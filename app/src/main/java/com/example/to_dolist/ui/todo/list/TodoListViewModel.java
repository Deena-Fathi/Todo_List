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

/**
 * ViewModel for managing the list of to-do items. <br>
 *
 * - repository        The repository for accessing and updating to-do data. <br>
 * - todos             LiveData holding the list of active to-do items. <br>
 * - getTodos          Returns LiveData with active to-do items, excluding completed ones. <br>
 * - getTodosDone      Returns LiveData with completed to-do items. <br>
 * - onTodoCheckedChange Updates the to-do item when its completion status changes.
 */
public class TodoListViewModel extends AndroidViewModel {

    @NonNull
    private final TodosRepository repository;

    @NonNull
    private final LiveData<List<Todo>> todos;

    /**
     * Constructs a TodoListViewModel.
     *
     * @param application The application context.
     */
    public TodoListViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TodosRepository(application);
        this.todos = repository.getTodos();
    }

    /**
     * Returns LiveData with active to-do items, excluding completed ones.
     *
     * @return LiveData with active to-do items.
     */
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

    /**
     * Returns LiveData with completed to-do items.
     *
     * @return LiveData with completed to-do items.
     */
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

    /**
     * Updates the to-do item when its completion status changes.
     *
     * @param todo    The to-do item to be updated.
     * @param checked The new completion status of the to-do item.
     */
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
