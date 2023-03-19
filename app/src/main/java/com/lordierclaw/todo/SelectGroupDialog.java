package com.lordierclaw.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lordierclaw.todo.adapter.GroupAdapter;
import com.lordierclaw.todo.listener.IGroupListener;

public class SelectGroupDialog extends BottomSheetDialog {
    private RecyclerView groupRecyclerView;
    private final IGroupListener iGroupListener;

    public SelectGroupDialog(@NonNull Context context, IGroupListener iGroupListener) {
        super(context);
        this.iGroupListener = iGroupListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setData();
    }

    private void initUI() {
        View viewDialog = getLayoutInflater().inflate(R.layout.group_select_layout, null);
        setContentView(viewDialog);
        groupRecyclerView = findViewById(R.id.groupRecyclerView);
    }

    private void setData() {
        GroupAdapter groupAdapter = new GroupAdapter(iGroupListener);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        groupRecyclerView.setAdapter(groupAdapter);
    }
}
