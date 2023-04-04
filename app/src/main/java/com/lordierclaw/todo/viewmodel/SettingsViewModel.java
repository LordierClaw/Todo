package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.lordierclaw.todo.utils.sharedprefs.SharedPrefsManager;
import com.lordierclaw.todo.utils.database.task.TaskRepository;

public class SettingsViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    public SettingsViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application.getApplicationContext());
    }

    public boolean logIn() {
        return false;
    }

    public boolean logOut() {
        return false;
    }

    public boolean sync() {
        return false;
    }

    public void setDarkMode(boolean isEnabled) {
        SharedPrefsManager.getInstance().setDarkModeEnabled(isEnabled);
    }

    public boolean isDarkMode() {
        return SharedPrefsManager.getInstance().isDarkModeEnabled();
    }

    public boolean generateDummyData() {
        return true;
    }

    public boolean deleteAllTasks() {
        taskRepository.deleteAllData();
        return true;
    }

    public boolean clearData() {
        deleteAllTasks();
        SharedPrefsManager.getInstance().clear();
        return true;
    }
}