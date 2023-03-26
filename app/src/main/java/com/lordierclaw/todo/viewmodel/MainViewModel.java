package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.utils.database.TaskRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final TaskRepository mRepository;
    private final LiveData<List<Task>> mAllTask;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application.getApplicationContext());
        mAllTask = mRepository.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    public void insert(Task task) {
        mRepository.insert(task);
    }
}
