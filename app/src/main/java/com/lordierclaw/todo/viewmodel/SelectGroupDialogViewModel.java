package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupRepository;

import java.util.List;

public class SelectGroupDialogViewModel extends AndroidViewModel {
    private final TaskGroupRepository mRepository;

    public SelectGroupDialogViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskGroupRepository(application.getApplicationContext());
    }

    public LiveData<List<TaskGroup>> getGroupList() {
        return mRepository.getList();
    }
}
