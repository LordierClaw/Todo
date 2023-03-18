package com.lordierclaw.todo.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lordierclaw.todo.R;
import java.util.List;

import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;
    private final ITaskListener iTaskListener;
    public TaskAdapter(List<Task> tasks, ITaskListener iTaskListener) {
        this.tasks = tasks;
        this.iTaskListener = iTaskListener;
    }

    public List<Task> getTasksList() {
        return tasks;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasksList(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task task = tasks.get(position);
        if (task == null) return;
        holder.taskCheckBox.setText(task.getName());
        holder.taskCheckBox.setChecked(task.isCompleted());
        holder.taskDateText.setText(task.getDateString());
        holder.taskGroupText.setText(task.getGroup().toString());
        if (task.getDateString().equals("") && task.getGroup().toString().equals("")) {
            holder.taskDateText.setVisibility(View.GONE);
            holder.taskGroupText.setVisibility(View.GONE);
        }
        holder.taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTaskListener.onClickCheckbox(task, holder.getAbsoluteAdapterPosition());
            }
        });
        holder.taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTaskListener.onClick(task, holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tasks == null) return 0;
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout taskLayout;
        private final CheckBox taskCheckBox;
        private final TextView taskGroupText, taskDateText;
        public ViewHolder(@NonNull View view) {
            super(view);
            taskLayout = view.findViewById(R.id.taskLayout);
            taskCheckBox = view.findViewById(R.id.taskCheckBox);
            taskGroupText = view.findViewById(R.id.taskGroupText);
            taskDateText = view.findViewById(R.id.taskDateText);
        }
    }
}
