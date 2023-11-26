package com.example.to_dolist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
abstract class TodosDatabase extends RoomDatabase {

    abstract TodosDao dao();

    private static volatile TodosDatabase INSTANCE;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    static TodosDatabase getDatabase(@NonNull Context context) {
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
