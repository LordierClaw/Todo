package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.utils.database.task.TaskRepository;
import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final TaskGroupRepository mGroupRepository;
    private final TaskRepository mTaskRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mGroupRepository = new TaskGroupRepository(application.getApplicationContext());
        mTaskRepository = new TaskRepository(application.getApplicationContext());
    }

    public LiveData<List<TaskGroup>> getGroupList() {
        return mGroupRepository.getList();
    }

    public void deleteGroup(TaskGroup taskGroup) {
        mTaskRepository.deleteAllInGroup(taskGroup);
        mGroupRepository.delete(taskGroup);
    }
}
