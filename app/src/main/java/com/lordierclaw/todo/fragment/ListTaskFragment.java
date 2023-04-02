package com.lordierclaw.todo.fragment;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.TaskAdapter;
import com.lordierclaw.todo.listener.ITaskLayoutListener;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.viewmodel.ListTaskViewModel;

import java.util.List;

public class ListTaskFragment extends Fragment {
    private ListTaskViewModel mViewModel;
    private RecyclerView tasksRcv;
    private TaskAdapter taskAdapter;
    private ITaskLayoutListener iTaskLayoutListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_list_task, container, false);
        mViewModel = new ViewModelProvider(this).get(ListTaskViewModel.class);
        // Init UI and set Data
        tasksRcv = mView.findViewById(R.id.tasks_rcv);
        taskAdapter = new TaskAdapter();
        taskAdapter.setTaskCheckBoxListener((task, isChecked) -> mViewModel.updateTaskChecked(task, isChecked));
        taskAdapter.setTaskLayoutListener(task -> iTaskLayoutListener.onClick(task));
        mViewModel.getTaskList().observe(getViewLifecycleOwner(), tasks -> submitList(tasks));
        tasksRcv.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRcv.setAdapter(taskAdapter);
        return mView;
    }

    public void setTaskLayoutListener(ITaskLayoutListener listener) {
        iTaskLayoutListener = listener;
    }
    private void submitList(List<Task> tasks) {
        taskAdapter.submitList(tasks);
        if (tasks.size() == 0) {
            tasksRcv.setBackgroundResource(R.drawable.empty_list_bg);
        } else {
            tasksRcv.setBackgroundResource(0);
        }
    }

    public void setListType(TaskGroup group) {
        if (mViewModel.getTaskList().hasObservers()) mViewModel.getTaskList().removeObservers(getViewLifecycleOwner());
        mViewModel.setTaskListByGroup(group);
        mViewModel.getTaskList().observe(getViewLifecycleOwner(), tasks -> submitList(tasks));
    }


    public void setListType() {
        if (mViewModel.getTaskList().hasObservers()) mViewModel.getTaskList().removeObservers(getViewLifecycleOwner());
        mViewModel.setTaskListMyDay();
        mViewModel.getTaskList().observe(getViewLifecycleOwner(), tasks -> submitList(tasks));
    }
}