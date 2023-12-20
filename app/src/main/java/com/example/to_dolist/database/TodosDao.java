package com.example.to_dolist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface TodosDao {
    /**
     * Ignore insertion if a to-do with the same id already exists in the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTodo(Todo todo);

    @Query("SELECT * FROM todos ORDER BY date DESC")
    LiveData<List<Todo>> getTodos();

    @Update()
    void updateTodo(Todo todo);
}