package com.lordierclaw.todo;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.lordierclaw.todo.model.User;
import com.lordierclaw.todo.utils.SharedPrefsManager;
import com.lordierclaw.todo.utils.database.TaskDatabase;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsManager.init(getApplicationContext());
        TaskDatabase.initExecutor(8);
        try {
            User.setInstance(SharedPrefsManager.getInstance().getUserAccount());
        } catch (Exception e) {
            User.setNewInstance();
        }
        if (SharedPrefsManager.getInstance().isDarkModeEnabled()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
