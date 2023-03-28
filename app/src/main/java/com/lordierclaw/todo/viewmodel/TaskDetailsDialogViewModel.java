package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.utils.database.TaskRepository;

import java.util.Date;

public class TaskDetailsDialogViewModel extends AndroidViewModel {

    private final TaskRepository mRepository;
    private volatile MutableLiveData<Task> mTask;

    public TaskDetailsDialogViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application.getApplicationContext());
    }

    public MutableLiveData<Task> getTask() {
        if (mTask == null) {
            mTask = new MutableLiveData<Task>(null);
        }
        return mTask;
    }

    public void setTaskName(String name) {
        if (mTask.getValue() == null) return;
        mTask.getValue().setName(name);
        updateTask();
    }

    public void setTaskGroup(Task.TaskGroup group) {
        if (mTask.getValue() == null) return;
        mTask.getValue().setGroup(group);
        updateTask();
    }

    public void setTaskDate(Date date) {
        if (mTask.getValue() == null) return;
        mTask.getValue().setDate(date);
        updateTask();
    }

    public void updateTask() {
        mRepository.update(mTask.getValue());
        mTask.setValue(mTask.getValue());
    }
}
