package com.lordierclaw.todo.utils.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lordierclaw.todo.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({TaskConverters.class})
public abstract class TaskDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "task.db";
    private static volatile TaskDatabase instance;

    private static ExecutorService databaseExecutor;
    public static void initExecutor(int nThreads) {
         databaseExecutor = Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }

    public static TaskDatabase getInstance(Context mContext) {
        if (instance == null) {
            synchronized (TaskDatabase.class) {
                if (instance == null) instance = Room.databaseBuilder(
                        mContext.getApplicationContext(), TaskDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract TaskDAO taskDAO();
}
