package com.lordierclaw.todo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.security.PublicKey;
import java.util.List;

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

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();;
        editor.clear();
        editor.apply();
    }
}
