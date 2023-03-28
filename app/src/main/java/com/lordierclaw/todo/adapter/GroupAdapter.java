package com.lordierclaw.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private final List<Task.TaskGroup> groups;
    private final IGroupListener iGroupListener;

    public GroupAdapter(IGroupListener iGroupListener) {
        this.iGroupListener = iGroupListener;
        groups = new ArrayList<>();
        groups.addAll(Arrays.asList(Task.TaskGroup.values()));
        groups.remove(0); // remove None group
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
        Task.TaskGroup group = groups.get(position);
        if (group == null) return;
        holder.groupName.setText(group.toString());
        holder.groupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGroupListener.onClick(group, holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout groupRelativeLayout;
        private final ImageView groupIcon;
        private final TextView groupName;
        public ViewHolder(@NonNull View view) {
            super(view);
            groupRelativeLayout = view.findViewById(R.id.groupRelativeLayout);
            groupIcon = view.findViewById(R.id.adapter_group_icon);
            groupName = view.findViewById(R.id.adapter_group_name);
        }
    }
}
