package com.lordierclaw.todo.utils.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lordierclaw.todo.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({TaskConverters.class})
public abstract class TaskDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "task.db";
    private static TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext.getApplicationContext(), TaskDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract TaskDAO taskDAO();
}
