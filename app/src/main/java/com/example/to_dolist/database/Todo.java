package com.example.to_dolist.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private final int id;

    @NonNull
    private final String todo;

    @NonNull
    private final String date;

    private final boolean done;

    public Todo(int id, @NonNull String todo, @NonNull String date, boolean done) {
        this.id = id;
        this.todo = todo;
        this.date = date;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTodo() {
        return todo;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public boolean done() {
        return done;
    }
}