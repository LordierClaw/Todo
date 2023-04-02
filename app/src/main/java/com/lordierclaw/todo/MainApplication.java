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
        SharedPrefsManager.init(getApplicationContext());
        TaskDatabase.initExecutor(8);
        TaskGroupDatabase.initExecutor(4);
        try {
            User.setInstance(SharedPrefsManager.getInstance().getUserAccount());
        } catch (Exception e) {
            User.setNewInstance();
        }
        if (SharedPrefsManager.getInstance().isDarkModeEnabled()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
