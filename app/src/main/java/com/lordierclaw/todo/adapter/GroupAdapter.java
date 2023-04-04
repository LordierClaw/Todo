package com.lordierclaw.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.model.TaskGroup;

public class GroupAdapter extends ListAdapter<TaskGroup, GroupAdapter.ViewHolder> {
    private final IGroupListener iGroupListener;

    public GroupAdapter(IGroupListener iGroupListener) {
        super(DIFF_CALLBACK);
        this.iGroupListener = iGroupListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_group_layout, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskGroup group = getItem(position);
        if (group == null) return;
        holder.bind(group);
        holder.groupRelativeLayout.setOnClickListener(view -> iGroupListener.onClick(group, holder.getAbsoluteAdapterPosition()));
    }

    public static final DiffUtil.ItemCallback<TaskGroup> DIFF_CALLBACK = new DiffUtil.ItemCallback<TaskGroup>() {
        @Override
        public boolean areItemsTheSame(@NonNull TaskGroup oldItem, @NonNull TaskGroup newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskGroup oldItem, @NonNull TaskGroup newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout groupRelativeLayout;
        private final ImageView groupIcon;
        private final TextView groupName;
        public ViewHolder(@NonNull View view) {
            super(view);
            groupRelativeLayout = view.findViewById(R.id.adapter_group_layout);
            groupIcon = view.findViewById(R.id.adapter_group_icon);
            groupName = view.findViewById(R.id.adapter_group_name);
        }

        public void bind(TaskGroup taskGroup) {
            groupName.setText(taskGroup.getName());
        }
    }
}
