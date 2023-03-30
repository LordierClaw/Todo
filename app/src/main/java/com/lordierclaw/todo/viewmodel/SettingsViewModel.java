package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.utils.SharedPrefsManager;
import com.lordierclaw.todo.utils.TaskCalendar;
import com.lordierclaw.todo.utils.database.TaskRepository;

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
        taskRepository.insert(new Task("Buy milk", TaskCalendar.getDate(2023, 3, 31), Task.TaskGroup.Home));
        taskRepository.insert(new Task("Prepare tax return", TaskCalendar.getDate(2023, 3, 15), Task.TaskGroup.Work));
        taskRepository.insert(new Task("Take dog for a walk", TaskCalendar.getDate(2023, 3, 30), Task.TaskGroup.Personal));
        taskRepository.insert(new Task("Finish book chapter", TaskCalendar.getDate(2023, 4, 2), Task.TaskGroup.Education));
        taskRepository.insert(new Task("Organize closet", null, Task.TaskGroup.Home));
        taskRepository.insert(new Task("Research new project idea", TaskCalendar.getDate(2023, 4, 5), Task.TaskGroup.Work));
        taskRepository.insert(new Task("Attend club meeting", TaskCalendar.getDate(2023, 4, 8), Task.TaskGroup.CollegeAndClub));
        taskRepository.insert(new Task("Schedule doctor's appointment", null, Task.TaskGroup.Personal));
        taskRepository.insert(new Task("Study for exam", TaskCalendar.getDate(2023, 4, 11), Task.TaskGroup.Education));
        taskRepository.insert(new Task("Clean bathroom", TaskCalendar.getDate(2023, 4, 3), Task.TaskGroup.Home));
        taskRepository.insert(new Task("Prepare budget report", TaskCalendar.getDate(2023, 4, 6), Task.TaskGroup.Work));
        taskRepository.insert(new Task("Volunteer at local shelter", TaskCalendar.getDate(2023, 4, 9), Task.TaskGroup.Personal));
        taskRepository.insert(new Task("Meet with academic advisor", TaskCalendar.getDate(2023, 4, 12), Task.TaskGroup.Education));
        taskRepository.insert(new Task("Mow the lawn", TaskCalendar.getDate(2023, 4, 4), Task.TaskGroup.Home));
        taskRepository.insert(new Task("Attend team building event", TaskCalendar.getDate(2023, 4, 7), Task.TaskGroup.Work));
        taskRepository.insert(new Task("Organize fundraiser", TaskCalendar.getDate(2023, 4, 10), Task.TaskGroup.CollegeAndClub));
        taskRepository.insert(new Task("Get a haircut", null, Task.TaskGroup.Personal));
        taskRepository.insert(new Task("Prepare for presentation", TaskCalendar.getDate(2023, 4, 13), Task.TaskGroup.Education));
        taskRepository.insert(new Task("Paint bedroom", TaskCalendar.getDate(2023, 4, 1), Task.TaskGroup.Home));
        taskRepository.insert(new Task("Attend conference", TaskCalendar.getDate(2023, 4, 14), Task.TaskGroup.Work));
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