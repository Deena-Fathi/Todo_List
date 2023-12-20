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
/**
 * Represents the Room database for storing todo items. <br>
 * The database includes a single table for todos and utilizes a DAO (Data Access Object)
 * to perform CRUD operations. It also provides a method to obtain an instance of the database. <br>
 *
 *  @param entities            Array of entity classes (e.g., Todo) that define the database schema. <br>
 *  @param version             Database version. Increment if schema changes are made. <br>
 *  @param exportSchema        Indicates whether to export the schema. Set to false for simplicity. <br>
 *  @param TypeConverters      Specifies the type converters to use for handling complex data types. <br>
 *  @param dao                 Data Access Object providing methods to interact with the database. <br>
 *  @param INSTANCE            Singleton instance of the database, ensuring only one instance exists. <br>
 *  @param databaseWriteExecutor ExecutorService for performing database write operations concurrently. <br>
 *  @param getDatabase         Static method to obtain an instance of the TodosDatabase.
 */
abstract class TodosDatabase extends RoomDatabase {

    abstract TodosDao dao();

    private static volatile TodosDatabase INSTANCE;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    /**
     * Gets an instance of the TodosDatabase.
     * If an instance does not exist, creates a new one using Room's databaseBuilder.
     *
     * @param context Application context used for database creation.
     * @return The singleton instance of the TodosDatabase.
     */
    @NonNull
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
