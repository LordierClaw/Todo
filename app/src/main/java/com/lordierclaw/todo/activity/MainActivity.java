package com.lordierclaw.todo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.TaskAdapter;
import com.lordierclaw.todo.fragment.AddTaskDialogFragment;
import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // UI VARIABLE
    private RelativeLayout toolbar;
    private NestedScrollView mainScrollView;
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton newTaskFloatButton;

    // OTHER VARIABLE
    private AddTaskDialogFragment addTaskDialog;
    private MainViewModel mMainViewModel;

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
        addTaskDialog = new AddTaskDialogFragment();
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void initBehaviour() {
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
        newTaskFloatButton.setOnClickListener(view -> addTaskDialog.show(getSupportFragmentManager(), addTaskDialog.getTag()));
    }

    private void setData() {
        // Database Behaviour
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
        mMainViewModel.getAllTask().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);
    }
}