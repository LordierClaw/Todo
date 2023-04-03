package com.lordierclaw.todo.model;

import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "task")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Date date;
    private TaskGroup group;
    private boolean isCompleted;

    public Task(String name, Date date, TaskGroup group) {
        this.name = name;
        this.date = date;
        this.group = group;
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TaskGroup getGroup() {
        return group;
    }

    public void setGroup(TaskGroup group) {
        this.group = group;
    }

    public boolean equals(Task task) {
        if (task == null) return false;
        boolean isNameEqual = name.equals(task.getName());
        boolean isDateEqual = date.getTime() == task.getDate().getTime();
        boolean isGroupEqual = group.equals(task.getGroup());
        return isNameEqual && isDateEqual && isGroupEqual;
    }
}
