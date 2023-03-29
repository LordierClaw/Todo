package com.lordierclaw.todo.utils;

import android.content.Context;

public class SharedPrefsManager {

    public static class LocalDataKey {
        public static final String FIRST_INSTALLED = "FIRST_TIME_INSTALLED";
    }

    private static SharedPrefsManager instance;
    private SharedPrefs sharedPrefs;

    private SharedPrefsManager(){}

    private SharedPrefsManager(Context mContext) {
        sharedPrefs = new SharedPrefs(mContext);
    }

    public static void init(Context mContext) {
        instance = new SharedPrefsManager(mContext);
    }

    public static SharedPrefsManager getInstance() {
        if (instance == null) instance = new SharedPrefsManager();
        return instance;
    }

    public SharedPrefs getData() {
        return sharedPrefs;
    }

    public boolean getFirstInstalled() {
        return sharedPrefs.getBoolean(LocalDataKey.FIRST_INSTALLED);
    }

    public void setFirstInstalled(boolean value) {
        sharedPrefs.putBoolean(LocalDataKey.FIRST_INSTALLED, value);
    }
}
