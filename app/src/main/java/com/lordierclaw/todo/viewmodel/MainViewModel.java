package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final TaskGroupRepository mGroupRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mGroupRepository = new TaskGroupRepository(application.getApplicationContext());
    }

    public LiveData<List<TaskGroup>> getGroupList() {
        return mGroupRepository.getList();
    }

    public void insertTaskGroup(TaskGroup taskGroup) {
        mGroupRepository.insert(taskGroup);
    }
}
