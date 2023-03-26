package com.lordierclaw.todo;

import android.app.Application;

import com.lordierclaw.todo.model.Manager;
import com.lordierclaw.todo.utils.SharedPrefsManager;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsManager.init(getApplicationContext());
        Manager.init(getApplicationContext());
    }
}
