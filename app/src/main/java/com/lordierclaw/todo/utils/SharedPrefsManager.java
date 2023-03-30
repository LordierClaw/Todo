package com.lordierclaw.todo.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lordierclaw.todo.model.User;

public class SharedPrefsManager {

    public static class LocalDataKey {
        public static final String FIRST_INSTALLED = "FIRST_TIME_INSTALLED";
        public static final String USER_ACCOUNT = "USER_ACCOUNT";
        public static final String DARK_MODE_ENABLED = "DARK_MODE_ENABLED";
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

    public User getUserAccount() {
        String json = sharedPrefs.getString(LocalDataKey.USER_ACCOUNT);
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    public void setUserAccount(@NonNull User user) {
        sharedPrefs.putString(LocalDataKey.USER_ACCOUNT, user.toJson());
    }

    public boolean isDarkModeEnabled() {
        return sharedPrefs.getBoolean(LocalDataKey.DARK_MODE_ENABLED);
    }

    public void setDarkModeEnabled(boolean value) {
        sharedPrefs.putBoolean(LocalDataKey.DARK_MODE_ENABLED, value);
    }

    public void clear() {
        sharedPrefs.clear();
    }
}
