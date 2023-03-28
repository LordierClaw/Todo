package com.lordierclaw.todo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.TaskAdapter;
import com.lordierclaw.todo.fragment.AddTaskDialogFragment;
import com.lordierclaw.todo.fragment.TaskDetailsDialogFragment;
import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    // UI VARIABLE
    private RelativeLayout toolbar;
    private NestedScrollView mainScrollView;
    private TaskAdapter taskAdapter;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton newTaskFloatButton;

    // OTHER VARIABLE
    private AddTaskDialogFragment addTaskDialog;
    private TaskDetailsDialogFragment taskDetailsDialog;
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
        mainScrollView = findViewById(R.id.main_scroll_view);
        tasksRecyclerView = findViewById(R.id.tasks_rcv);
        newTaskFloatButton = findViewById(R.id.new_task_float_btn);
        addTaskDialog = new AddTaskDialogFragment();
        taskDetailsDialog = new TaskDetailsDialogFragment();
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void initBehaviour() {
        // Scroll View Behaviour
        mainScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > tasksRecyclerView.getPaddingTop()) {
                newTaskFloatButton.hide();
                toolbar.setElevation(12);
            } else {
                newTaskFloatButton.show();
                toolbar.setElevation(0);
            }
        });
        // Click Event
        newTaskFloatButton.setOnClickListener(view -> addTaskDialog.show(getSupportFragmentManager(), addTaskDialog.getTag()));
    }

    private void setData() {
        // Database Behaviour
        taskAdapter = new TaskAdapter(new ITaskListener() {
            @Override
            public void onClick(Task task) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TASK_TO_SHOW_DETAILS", task);
                taskDetailsDialog.setArguments(bundle);
                taskDetailsDialog.show(getSupportFragmentManager(), taskDetailsDialog.getTag());
            }

            @Override
            public void onClickCheckbox(Task task, boolean isChecked) {
                mMainViewModel.updateTaskChecked(task, isChecked);
            }
        });

        mMainViewModel.getAllTask().observe(this, tasks -> taskAdapter.submitList(tasks));
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);
    }
}