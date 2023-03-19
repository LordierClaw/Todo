package com.lordierclaw.todo.listener;

import com.lordierclaw.todo.model.Task;

public interface IGroupListener {
    void onClick(Task.TaskGroup taskGroup, int position);
}
