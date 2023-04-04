package com.lordierclaw.todo.utils.database.task;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.model.TaskGroup;

import java.util.Date;
import java.util.List;

public class TaskRepository {
    private final TaskDAO mTaskDAO;
    private Context mContext;

    public TaskRepository(Context context) {
        mContext = context;
        mTaskDAO = TaskDatabase.getInstance(mContext).taskDAO();
    }

    public LiveData<List<Task>> getTaskInGroup(TaskGroup group) {
        return mTaskDAO.getList(group);
    }

    public LiveData<List<Task>> getTaskMyDay(Date date) {
        return mTaskDAO.getList(date);
    }

    public LiveData<List<Task>> getAll() {
        return mTaskDAO.getAll();
    }

    public void insert(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(() -> mTaskDAO.insert(task));
    }

    public void update(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(() -> mTaskDAO.update(task));
    }

    public void delete(Task task) {
        TaskDatabase.getDatabaseExecutor().execute(() -> mTaskDAO.delete(task));
    }

    public void deleteAllData() {
        TaskDatabase.getDatabaseExecutor().execute(() -> TaskDatabase.getInstance(mContext).clearAllTables());
    }

    public void deleteAllInGroup(TaskGroup taskGroup) {
        TaskDatabase.getDatabaseExecutor().execute(() -> mTaskDAO.deleteAllInGroup(taskGroup));
    }

    public void updateToNewGroup(TaskGroup oldGroup, TaskGroup newGroup) {
        TaskDatabase.getDatabaseExecutor().execute(() -> mTaskDAO.updateToNewGroup(oldGroup, newGroup));
    }
}
