package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.utils.database.task.TaskRepository;
import com.lordierclaw.todo.utils.database.taskgroup.TaskGroupRepository;

public class GroupEditDialogViewModel extends AndroidViewModel {
    private TaskGroupRepository mGroupRepository;
    private TaskRepository mTaskRepository;
    private MutableLiveData<TaskGroup> mTaskGroup;

    public GroupEditDialogViewModel(@NonNull Application application) {
        super(application);
        mGroupRepository = new TaskGroupRepository(application.getApplicationContext());
        mTaskRepository = new TaskRepository(application.getApplicationContext());
    }

    public void insertNewGroup(TaskGroup taskGroup) {
        mGroupRepository.insert(taskGroup);
    }

    public void updateGroup(String name) {
        if (mTaskGroup == null || mTaskGroup.getValue() == null) return;
        TaskGroup newGroup = mTaskGroup.getValue();
        TaskGroup oldGroup = new TaskGroup(newGroup.getName());
        oldGroup.setId(newGroup.getId());
        newGroup.setName(name);
        if (oldGroup.getName().equals(newGroup.getName())) return;
        mTaskGroup.setValue(newGroup);
        mTaskRepository.updateToNewGroup(oldGroup, newGroup);
        mGroupRepository.update(newGroup);
    }

    public MutableLiveData<TaskGroup> getGroup() {
        if (mTaskGroup == null) mTaskGroup = new MutableLiveData<>();
        return mTaskGroup;
    }

    public void unbindGroup() {
        if (mTaskGroup == null) return;
        if (mTaskGroup.getValue() != null) mTaskGroup.setValue(null);
    }

    public void bindGroup(TaskGroup group) {
        if (mTaskGroup == null) mTaskGroup = new MutableLiveData<>();
        mTaskGroup.setValue(group);
    }
}