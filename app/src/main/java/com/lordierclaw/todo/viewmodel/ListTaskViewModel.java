package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.utils.TaskCalendar;
import com.lordierclaw.todo.viewmodel.utils.database.TaskRepository;

import java.util.List;

public class ListTaskViewModel extends AndroidViewModel {
    private final TaskRepository mRepository;
    private LiveData<List<Task>> mTaskList;

    public ListTaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application.getApplicationContext());
        setTaskListMyDay();
    }

    public LiveData<List<Task>> getTaskList() {
        return mTaskList;
    }

    public void setTaskListByGroup(Task.TaskGroup group) {
        mTaskList = mRepository.getTaskInGroup(group);
    }
    public void setTaskListMyDay() {
        mTaskList = mRepository.getTaskMyDay(TaskCalendar.getNow());
    }

    public void insertTask(Task task) {
        mRepository.insert(task);
    }

    public void updateTaskChecked(Task task, boolean isChecked) {
        task.setCompleted(isChecked);
        mRepository.update(task);
    }
}