package com.lordierclaw.todo.listener;

import com.lordierclaw.todo.model.Task;

public interface ITaskCheckBoxListener {
    void onClick(Task task, boolean isChecked);
}
