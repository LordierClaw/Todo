package com.lordierclaw.todo.utils.database.taskgroup;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.TaskGroup;

import java.util.List;

public class TaskGroupRepository {
    private final TaskGroupDAO mDAO;
    private Context mContext;

    public TaskGroupRepository(Context mContext) {
        this.mContext = mContext;
        mDAO = TaskGroupDatabase.getInstance(mContext).taskGroupDAO();
    }

    public LiveData<List<TaskGroup>> getList() {
        return mDAO.getList();
    }

    public void insert(TaskGroup taskGroup) {
        TaskGroupDatabase.getDatabaseExecutor().execute(() -> mDAO.insert(taskGroup));
    }

    public void update(TaskGroup taskGroup) {
        TaskGroupDatabase.getDatabaseExecutor().execute(() -> mDAO.update(taskGroup));
    }

    public void delete(TaskGroup taskGroup) {
        TaskGroupDatabase.getDatabaseExecutor().execute(() -> mDAO.delete(taskGroup));
    }

    public void deleteAllData() {
        TaskGroupDatabase.getDatabaseExecutor().execute(() -> TaskGroupDatabase.getInstance(mContext).clearAllTables());
    }
}
