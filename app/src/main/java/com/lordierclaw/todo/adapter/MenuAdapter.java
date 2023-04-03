package com.lordierclaw.todo.adapter;

import android.content.Context;
import android.util.TypedValue;
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
import com.lordierclaw.todo.model.CustomMenuItem;

public class MenuAdapter extends ListAdapter<CustomMenuItem, MenuAdapter.ViewHolder> {
    private MenuAdapter.ViewHolder lastHolder = null;
    private final TypedValue colorChecked;

    public MenuAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        colorChecked = new TypedValue();
        mContext.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondaryContainer, colorChecked, true);
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
                lastHolder.menuLayout.setBackgroundColor(0);
            }
            item.setChecked(true);
            lastHolder = holder;
            holder.menuLayout.setBackgroundColor(colorChecked.data);
        });
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
