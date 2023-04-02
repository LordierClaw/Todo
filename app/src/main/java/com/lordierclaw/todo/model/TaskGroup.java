package com.lordierclaw.todo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.Objects;

@Entity(tableName = "task-group")
public class TaskGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public TaskGroup(String name) {
        this.name = name;
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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskGroup taskGroup = (TaskGroup) o;
        return id == taskGroup.id && Objects.equals(name, taskGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
