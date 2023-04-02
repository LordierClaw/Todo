package com.lordierclaw.todo.utils.database.task;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.lordierclaw.todo.model.TaskGroup;

import java.util.Date;

public class TaskConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if (value == null) return null;
        return new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) return null;
        return date.getTime();
    }

    @TypeConverter
    public static TaskGroup fromJson(String value) {
        if (value == null) return null;
        Gson gson = new Gson();
        return gson.fromJson(value, TaskGroup.class);
    }

    @TypeConverter
    public static String taskGroupToJson(TaskGroup taskGroup) {
        if (taskGroup == null) return null;
        return taskGroup.toJson();
    }
}
