package com.lordierclaw.todo.viewmodel.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPrefs {
    public static final String SHARED_PREFS_NAME = "TODO_SHARED_PREFS";
    private final SharedPreferences sharedPreferences;

    public SharedPrefs(@NonNull Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
