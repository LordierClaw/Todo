package com.lordierclaw.todo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.TaskAdapter;
import com.lordierclaw.todo.dialog.AddTaskDialog;
import com.lordierclaw.todo.listener.IManagerListener;
import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Manager;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.utils.database.TaskDatabase;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout toolbar;
    private NestedScrollView mainScrollView;
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton newTaskFloatButton;

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
        // Manager Event
        Manager.getInstance().setManagerListener(new IManagerListener() {
            @Override
            public void taskAdded() {
                taskAdapter.submitList(Manager.getInstance().getData());
            }
            @Override
            public void taskRemovedAt(int position) {
                taskAdapter.submitList(Manager.getInstance().getData());
            }
        });
        // Scroll View Behaviour
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
        // Click Event
        newTaskFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog.show();
            }
        });
    }

    private void setData() {
        taskAdapter = new TaskAdapter(new ITaskListener() {
            @Override
            public void onClick(Task task, int position) {
                //TODO: Click Task to open DetailActivity
            }

            @Override
            public void onClickCheckbox(Task task, int position) {
//                task.setCompleted(!task.isCompleted());
            }
        });
        taskAdapter.submitList(Manager.getInstance().getData());
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);
    }
}