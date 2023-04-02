package com.lordierclaw.todo.utils.database.taskgroup;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lordierclaw.todo.model.TaskGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaskGroup.class}, version = 1, exportSchema = false)
public abstract class TaskGroupDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "task-group.db";
    private static volatile TaskGroupDatabase instance;

    private static ExecutorService databaseExecutor;

    public static void initExecutor(int nThreads) {
        databaseExecutor = Executors.newFixedThreadPool(nThreads);
    }

    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }

    public static TaskGroupDatabase getInstance(Context mContext) {
        if (instance == null) {
            synchronized (TaskGroupDatabase.class) {
                if (instance == null) instance = Room.databaseBuilder(
                                mContext.getApplicationContext(), TaskGroupDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract TaskGroupDAO taskGroupDAO();
}
