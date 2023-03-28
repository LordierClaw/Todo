package com.lordierclaw.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.utils.database.TaskRepository;

import java.util.Date;

public class AddTaskDialogViewModel extends AndroidViewModel {

    private final TaskRepository mRepository;
    private MutableLiveData<Task.TaskGroup> selectedGroup;
    private MutableLiveData<Date> selectedDate;

    public AddTaskDialogViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application.getApplicationContext());
    }

    public MutableLiveData<Task.TaskGroup> getSelectedGroup() {
        if (selectedGroup == null) {
            selectedGroup = new MutableLiveData<Task.TaskGroup>(Task.TaskGroup.None);
        }
        return selectedGroup;
    }

    public MutableLiveData<Date> getSelectedDate() {
        if (selectedDate == null) {
            selectedDate = new MutableLiveData<Date>(null);
        }
        return selectedDate;
    }

    public String getGroupString() {
        if (selectedGroup.getValue() == null || selectedGroup.getValue() == Task.TaskGroup.None)
            return getApplication().getResources().getString(R.string.new_task_group_text);
        else return selectedGroup.getValue().toString();
    }

    public String getDateString() {
        if (selectedDate.getValue() == null)
            return getApplication().getResources().getString(R.string.new_task_date_text);
        else return Task.dateFormat.format(selectedDate.getValue());
    }

    public void setGroup(Task.TaskGroup group) {
        selectedGroup.setValue(group);
    }

    public void setDate(Date date) {
        selectedDate.setValue(date);
    }

    public void resetData() {
        setGroup(Task.TaskGroup.None);
        setDate(null);
    }

    public void addTask(String taskName) {
        mRepository.insert(new Task(taskName, getSelectedDate().getValue(), getSelectedGroup().getValue()));
    }

    public void updateTask(Task task) {
        mRepository.update(task);
    }
}
