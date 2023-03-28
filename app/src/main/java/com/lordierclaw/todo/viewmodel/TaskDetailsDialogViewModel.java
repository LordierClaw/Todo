package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.utils.database.TaskRepository;

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

    public String getGroupString() {
        if (mTask.getValue() == null || mTask.getValue().getGroup() == Task.TaskGroup.None)
            return getApplication().getResources().getString(R.string.new_task_group_text);
        else return mTask.getValue().getGroup().toString();
    }

    public String getDateString() {
        if (mTask.getValue() == null || mTask.getValue().getDate() == null)
            return getApplication().getResources().getString(R.string.new_task_date_text);
        else return mTask.getValue().getDateString();
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

    public void deleteTask() {
        mRepository.delete(mTask.getValue());
        mTask.setValue(null);
    }
}
