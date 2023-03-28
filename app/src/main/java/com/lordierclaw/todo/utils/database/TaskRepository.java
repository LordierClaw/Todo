package com.lordierclaw.todo.utils.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDAO mTaskDAO;
    private LiveData<List<Task>> mAllTask;

    public TaskRepository(Context context) {
        mTaskDAO = TaskDatabase.getInstance(context).taskDAO();
        mAllTask = mTaskDAO.getList();
    }

    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    public void insert(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mTaskDAO.insert(task);
            }
        });
    }

    public void update(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mTaskDAO.update(task);
            }
        });
    }

}
