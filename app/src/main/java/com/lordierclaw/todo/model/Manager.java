package com.lordierclaw.todo.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lordierclaw.todo.listener.IManagerListener;
import com.lordierclaw.todo.utils.database.TaskDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {
    private static Manager instance;
    private Context mContext;
    private IManagerListener iManagerListener;

    public static void init(Context mContext) {
        instance = null;
        instance = new Manager(mContext);
    }

    public static Manager getInstance(){
        if (instance == null) instance = new Manager();
        return instance;
    }

    private Manager() {

    }
    private Manager(Context mContext) {
        this();
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setManagerListener(IManagerListener iManagerListener) {
        this.iManagerListener = iManagerListener;
    }

    public void add(Task task) {
        if (mContext == null) return;
        TaskDatabase.getInstance(mContext).taskDAO().insert(task);
        if (iManagerListener == null) return;
        iManagerListener.taskAdded();
    }

    public void remove(String id) {
        if (mContext == null) return;
        int position = TaskDatabase.getInstance(mContext).taskDAO().delete(id);
        if (iManagerListener == null) return;
        iManagerListener.taskRemovedAt(position);
    }

    public List<Task> getData() {
        return TaskDatabase.getInstance(mContext).taskDAO().getList();
    }
}
