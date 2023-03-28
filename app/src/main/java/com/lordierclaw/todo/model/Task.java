package com.lordierclaw.todo.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "task")
public class Task implements Serializable {
    public enum TaskGroup{
        None(""),
        Home("Home"),
        Work("Work"),
        Education("Education"),
        Personal("Personal"),
        CollegeAndClub("College and Club");

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

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private Date date;
    private TaskGroup group;
    private boolean isCompleted;
    @Ignore
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Task(String name, Date date, TaskGroup group) {
        this.name = name;
        this.date = date;
        if (group != null) this.group = group;
        else this.group = TaskGroup.None;
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

    public boolean equals(Task task) {
        if (task == null) return false;
        boolean isNameEqual = name.equals(task.getName());
        boolean isDateEqual = getDateString().equals(task.getDateString());
        boolean isGroupEqual = group.equals(task.getGroup());
        return isNameEqual && isDateEqual && isGroupEqual;
    }
}
