package com.lordierclaw.todo.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private static int Counter = 0;
    public enum TaskGroup{
        None(""),
        Home("Home"),
        Work("Work");

        private final String name;

        TaskGroup(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    private String id, name;
    private Date date;
    private TaskGroup group;
    private boolean isCompleted;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Task(String name) {
        this.id = String.format("%05d", ++Counter);
        this.name = name;
        this.isCompleted = false;
        this.date = null;
        this.group = TaskGroup.None;
    }

    public Task(String name, Date date) {
        this(name);
        this.date = date;
    }

    public Task(String name, Date date, TaskGroup group) {
        this(name, date);
        this.group = group;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDateString() {
        if (date == null) return "";
        return Task.dateFormat.format(date);
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

}
