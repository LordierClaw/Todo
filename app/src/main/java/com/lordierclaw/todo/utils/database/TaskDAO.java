package com.lordierclaw.todo.utils.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lordierclaw.todo.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Query("SELECT * FROM task")
    List<Task> getList();
}
