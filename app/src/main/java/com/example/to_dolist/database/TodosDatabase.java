package com.example.to_dolist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class TodosDatabase extends RoomDatabase {

    public abstract TodosDao dao();

    private static volatile TodosDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TodosDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodosDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TodosDatabase.class,
                            "todos_database").build();
                }
            }
        }
        return INSTANCE;
    }
}