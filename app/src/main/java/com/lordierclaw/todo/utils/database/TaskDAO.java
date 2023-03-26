package com.lordierclaw.todo.utils.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lordierclaw.todo.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Delete
    int delete(Task task);

    @Query("DELETE FROM task WHERE id = :id")
    int delete(String id);

    @Update
    void update(Task task);

    @Query("SELECT * FROM task")
    List<Task> getList();
}
