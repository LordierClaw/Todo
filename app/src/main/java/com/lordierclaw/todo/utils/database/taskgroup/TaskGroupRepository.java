package com.lordierclaw.todo.utils.database.taskgroup;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.model.TaskGroup;

import java.util.List;

public class TaskGroupRepository {
    private final TaskGroupDAO mDAO;

    public TaskGroupRepository(Context mContext) {
        mDAO = TaskGroupDatabase.getInstance(mContext).taskGroupDAO();
    }

    public LiveData<List<TaskGroup>> getList() {
        return mDAO.getList();
    }

    public void insert(TaskGroup taskGroup) {
        TaskGroupDatabase.getDatabaseExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDAO.insert(taskGroup);
            }
        });
    }
}
