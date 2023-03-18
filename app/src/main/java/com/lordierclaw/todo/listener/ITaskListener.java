package com.lordierclaw.todo.listener;

import com.lordierclaw.todo.model.Task;

public interface ITaskListener {
    void onClick(Task task, int position);
    void onClickCheckbox(Task task, int position);
}
