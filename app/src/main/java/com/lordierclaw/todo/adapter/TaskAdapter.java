package com.lordierclaw.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lordierclaw.todo.R;

import com.lordierclaw.todo.listener.ITaskListener;
import com.lordierclaw.todo.model.Task;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {
    private final ITaskListener iTaskListener;
    public TaskAdapter(ITaskListener iTaskListener) {
        super(DIFF_CALLBACK);
        this.iTaskListener = iTaskListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task task = getItem(position);
        if (task == null) return;
        holder.bind(task);
        holder.taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTaskListener.onClickCheckbox(task, holder.taskCheckBox.isChecked());
            }
        });
        holder.taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iTaskListener.onClick(task);
            }
        });
    }

    public static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout taskLayout;
        private final CheckBox taskCheckBox;
        private final TextView taskGroupText, taskDateText;
        public ViewHolder(@NonNull View view) {
            super(view);
            taskLayout = view.findViewById(R.id.adapter_task_layout);
            taskCheckBox = view.findViewById(R.id.adapter_task_checkbox);
            taskGroupText = view.findViewById(R.id.taskGroupText);
            taskDateText = view.findViewById(R.id.adapter_date_text);
        }

        public void bind(Task task) {
            taskCheckBox.setText(task.getName());
            taskCheckBox.setChecked(task.isCompleted());
            taskDateText.setText(task.getDateString());
            taskGroupText.setText(task.getGroup().toString());
            if (task.getDateString().equals("") ) taskDateText.setVisibility(View.GONE);
            else taskDateText.setVisibility(View.VISIBLE);
            if (task.getGroup().toString().equals("")) taskGroupText.setVisibility(View.GONE);
            else taskGroupText.setVisibility(View.VISIBLE);
        }
    }
}
