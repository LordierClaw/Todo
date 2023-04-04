package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupRepository;
import com.lordierclaw.todo.utils.sharedprefs.SharedPrefsManager;
import com.lordierclaw.todo.utils.database.task.TaskRepository;

public class SettingsViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private TaskGroupRepository groupRepository;
    public SettingsViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application.getApplicationContext());
        groupRepository = new TaskGroupRepository(application.getApplicationContext());
    }

    public void setDarkMode(boolean isEnabled) {
        SharedPrefsManager.getInstance().setDarkModeEnabled(isEnabled);
    }

    public boolean isDarkMode() {
        return SharedPrefsManager.getInstance().isDarkModeEnabled();
    }

    public boolean deleteAllTasks() {
        taskRepository.deleteAllData();
        return true;
    }

    public boolean clearData() {
        taskRepository.deleteAllData();
        groupRepository.deleteAllData();
        SharedPrefsManager.getInstance().clear();
        return true;
    }
}