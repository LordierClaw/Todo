package com.lordierclaw.todo;

import android.app.Application;

import com.lordierclaw.todo.utils.SharedPrefsManager;
import com.lordierclaw.todo.utils.database.TaskDatabase;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsManager.init(getApplicationContext());
        TaskDatabase.initExecutor(8);
    }
}
