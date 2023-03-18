package com.lordierclaw.todo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {
    private static Manager instance;
    public static Manager getInstance(){
        if (instance == null) instance = new Manager();
        return instance;
    }

    private final List<Task> taskList;

    private Manager() {
        taskList = new ArrayList<>();
    }

    public void generateDummyData() {
        taskList.add(new Task("Buy groceries", new Date(), Task.TaskGroup.Home));
        taskList.add(new Task("Finish report", new Date(), Task.TaskGroup.Work));
        taskList.add(new Task("Clean the house", new Date(), Task.TaskGroup.Home));
        taskList.add(new Task("Call client", new Date(), Task.TaskGroup.Work));
        taskList.add(new Task("Do laundry", new Date(), Task.TaskGroup.Home));
        taskList.add(new Task("Prepare presentation", new Date(), Task.TaskGroup.Work));
        taskList.add(new Task("Pay bills", null, Task.TaskGroup.Home));
        taskList.add(new Task("Attend meeting", null, Task.TaskGroup.Work));
        taskList.add(new Task("Go to the gym", null, Task.TaskGroup.Home));
        taskList.add(new Task("Send email", null, Task.TaskGroup.Work));
        taskList.add(new Task("Take out the trash"));
        taskList.add(new Task("Buy a gift for mom"));
        taskList.add(new Task("Call dad"));
        taskList.add(new Task("Pick up dry cleaning"));
    }

    public void add(Task task) {
        taskList.add(task);
    }

    public void remove(int position) {
        taskList.remove(position);
    }

    public List<Task> getData() {
        return taskList;
    }
}
