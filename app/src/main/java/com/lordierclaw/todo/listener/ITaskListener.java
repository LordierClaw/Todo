package com.lordierclaw.todo.listener;

import com.lordierclaw.todo.model.Task;

public interface ITaskListener {
    void onClick(Task task);
    void onClickCheckbox(Task task, boolean isChecked);
}
