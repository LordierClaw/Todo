package com.lordierclaw.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lordierclaw.todo.adapter.TaskAdapter;
import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout toolbar;
    private NestedScrollView mainScrollView;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton newTaskFloatButton;
    private List<Task> mListTask;

    AddTaskDialog addTaskDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initBehaviour();
        setData();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        mainScrollView = findViewById(R.id.mainScrollView);
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        newTaskFloatButton = findViewById(R.id.newTaskFloatButton);
        addTaskDialog = new AddTaskDialog(this);
    }

    private void initBehaviour() {
        mainScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > tasksRecyclerView.getPaddingTop()) {
                    newTaskFloatButton.hide();
                    toolbar.setElevation(12);
                } else {
                    newTaskFloatButton.show();
                    toolbar.setElevation(0);
                }
            }
        });
        newTaskFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog.show();
            }
        });
    }

    private List<Task> getTasksList() {
        if (mListTask != null) return mListTask;
        // Generate dummy data for testing
        mListTask = new ArrayList<>();
        mListTask.add(new Task("Buy groceries", new Date(), Task.TaskGroup.Home));
        mListTask.add(new Task("Finish report", new Date(), Task.TaskGroup.Work));
        mListTask.add(new Task("Clean the house", new Date(), Task.TaskGroup.Home));
        mListTask.add(new Task("Call client", new Date(), Task.TaskGroup.Work));
        mListTask.add(new Task("Do laundry", new Date(), Task.TaskGroup.Home));
        mListTask.add(new Task("Prepare presentation", new Date(), Task.TaskGroup.Work));
        mListTask.add(new Task("Pay bills", null, Task.TaskGroup.Home));
        mListTask.add(new Task("Attend meeting", null, Task.TaskGroup.Work));
        mListTask.add(new Task("Go to the gym", null, Task.TaskGroup.Home));
        mListTask.add(new Task("Send email", null, Task.TaskGroup.Work));
        mListTask.add(new Task("Take out the trash"));
        mListTask.add(new Task("Buy a gift for mom"));
        mListTask.add(new Task("Call dad"));
        mListTask.add(new Task("Pick up dry cleaning"));
        return mListTask;
    }

    private void setData() {
        TaskAdapter taskAdapter = new TaskAdapter(getTasksList(), new ITaskListener() {
            @Override
            public void onClick(Task task, int position) {
                //TODO: Click Task to open DetailActivity
            }

            @Override
            public void onClickCheckbox(Task task, int position) {
                //TODO: Click TaskCheckbox to update value
            }
        });
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);
    }
}