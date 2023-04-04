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

public class MenuAdapter extends ListAdapter<CustomMenuItem, RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_DIVIDER = 2;
    private ItemViewHolder lastHolder = null;

    public MenuAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        mContext.getTheme().resolveAttribute(com.google.android.material.R.attr.colorSecondaryContainer, ItemViewHolder.colorChecked, true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_layout, null);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_DIVIDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_divider, null);
            return new DividerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomMenuItem item = getItem(position);
        if (item == null) return;
        if (getItemViewType(position) == TYPE_DIVIDER) return;
        ((ItemViewHolder)holder).bind(item);
        holder.itemView.setOnClickListener(view -> {
            item.getListener().onClick();
            if (lastHolder != null) {
                lastHolder.setSelected(false);
            }
            ((ItemViewHolder)holder).setSelected(true);
            lastHolder = (ItemViewHolder)holder;
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isDivider()) return TYPE_DIVIDER;
        return TYPE_ITEM;
    }

    public static final DiffUtil.ItemCallback<CustomMenuItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CustomMenuItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CustomMenuItem oldItem, @NonNull CustomMenuItem newItem) {
            if (oldItem.isDivider() && newItem.isDivider()) return true;
            else if (!oldItem.isDivider() && !newItem.isDivider()) return oldItem.getListener().equals(newItem.getListener());
            else return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CustomMenuItem oldItem, @NonNull CustomMenuItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout menuLayout;
        private ImageView menuIcon;
        private TextView menuTitle;
        private static final TypedValue colorChecked = new TypedValue();

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            menuLayout = itemView.findViewById(R.id.adapter_menu_layout);
            menuIcon = itemView.findViewById(R.id.adapter_menu_icon);
            menuTitle = itemView.findViewById(R.id.adapter_menu_title);
        }

        public void bind(CustomMenuItem item) {
            menuTitle.setText(item.getTitle());
            menuIcon.setImageDrawable(item.getIcon());
        }

        public void setSelected(boolean value) {
            if (value) menuLayout.setBackgroundColor(colorChecked.data);
            else menuLayout.setBackgroundColor(0);
        }
    }
}
