package com.lordierclaw.todo.utils.database.taskgroup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lordierclaw.todo.model.TaskGroup;

import java.util.List;

@Dao
public interface TaskGroupDAO {
    @Insert
    void insert(TaskGroup taskGroup);

    @Delete
    void delete(TaskGroup taskGroup);

    @Update
    void update(TaskGroup taskGroup);

    @Query("SELECT * FROM `task-group`")
    LiveData<List<TaskGroup>> getList();
}
