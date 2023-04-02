package com.lordierclaw.todo.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.listener.IMenuListener;
import com.lordierclaw.todo.model.CustomMenuItem;
import com.lordierclaw.todo.model.TaskGroup;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ListAdapter<CustomMenuItem, MenuAdapter.ViewHolder> {
    private IMenuListener iMenuListener;
    private Context mContext;
    private MenuAdapter.ViewHolder lastHolder = null;

    public MenuAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_layout, null);
        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        CustomMenuItem item = getItem(position);
        if (item == null) return;
        holder.bind(item);
        holder.menuLayout.setOnClickListener(view -> {
            item.getListener().onClick();
            if (lastHolder != null) {
                getItem(lastHolder.getAbsoluteAdapterPosition()).setChecked(false);
            }
            item.setChecked(true);
            lastHolder = holder;
            // TODO: set Checked and Unchecked Style. Maybe using custom view
        });
    }

    public void setOnClickListener(IMenuListener listener) {
        iMenuListener = listener;
    }

    public static final DiffUtil.ItemCallback<CustomMenuItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CustomMenuItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CustomMenuItem oldItem, @NonNull CustomMenuItem newItem) {
            return oldItem.getListener().equals(newItem.getListener());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CustomMenuItem oldItem, @NonNull CustomMenuItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout menuLayout;
        private ImageView menuIcon;
        private TextView menuTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuLayout = itemView.findViewById(R.id.adapter_menu_layout);
            menuIcon = itemView.findViewById(R.id.adapter_menu_icon);
            menuTitle = itemView.findViewById(R.id.adapter_menu_title);
        }

        public void bind(CustomMenuItem item) {
            menuTitle.setText(item.getTitle());
            menuIcon.setImageDrawable(item.getIcon());
        }

    }
}
