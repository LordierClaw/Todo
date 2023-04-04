package com.lordierclaw.todo;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.lordierclaw.todo.model.User;
import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupDatabase;
import com.lordierclaw.todo.utils.sharedprefs.SharedPrefsManager;
import com.lordierclaw.todo.utils.database.task.TaskDatabase;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Database
        TaskDatabase.initExecutor(8);
        TaskGroupDatabase.initExecutor(4);
        // Share Preferences
        SharedPrefsManager.init(getApplicationContext());
        try {
            User.setInstance(SharedPrefsManager.getInstance().getUserAccount());
        } catch (Exception e) {
            User.setNewInstance();
        }
        if (SharedPrefsManager.getInstance().isDarkModeEnabled()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
