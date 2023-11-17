package com.example.to_dolist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodosDao {

    // Prevent insertion if the passed to-do conflicts
    // with an existing to-do in the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTodo(Todo todo);

    @Query("SELECT * FROM todos ORDER BY date DESC")
    LiveData<List<Todo>> getTodos();

    @Update()
    void updateTodo(Todo todo);

    @Query("DELETE FROM todos WHERE id = :id")
    void deleteTodoById(int id);

    @Query("DELETE FROM todos")
    void deleteAllTodos();
}