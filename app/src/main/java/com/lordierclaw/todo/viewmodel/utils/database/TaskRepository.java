package com.lordierclaw.todo.viewmodel.utils.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.Task;

import java.util.Date;
import java.util.List;

public class TaskRepository {
    private final TaskDAO mTaskDAO;

    public TaskRepository(Context context) {
        mTaskDAO = TaskDatabase.getInstance(context).taskDAO();
    }

    public LiveData<List<Task>> getTaskInGroup(Task.TaskGroup group) {
        return mTaskDAO.getList(group);
    }

    public LiveData<List<Task>> getTaskMyDay(Date date) {
        return mTaskDAO.getList(date);
    }

    public LiveData<List<Task>> getAll() {
        return mTaskDAO.getAll();
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

    public void delete(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mTaskDAO.delete(task);
            }
        });
    }
}
