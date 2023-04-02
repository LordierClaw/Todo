package com.lordierclaw.todo.listener;

import com.lordierclaw.todo.model.TaskGroup;

public interface IGroupListener {
    void onClick(TaskGroup taskGroup, int position);
}
