package com.lordierclaw.todo.utils.database.task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.model.TaskGroup;

import java.util.Date;
import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task WHERE `group` = :taskGroup")
    LiveData<List<Task>> getList(TaskGroup taskGroup);

    @Query("SELECT * FROM task WHERE `date` = :date")
    LiveData<List<Task>> getList(Date date);
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();
}
