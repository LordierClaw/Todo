package com.lordierclaw.todo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.GroupAdapter;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.viewmodel.SelectGroupDialogViewModel;

import java.util.List;

public class SelectGroupDialogFragment extends BottomSheetDialogFragment {
    private IGroupListener iGroupListener;
    private SelectGroupDialogViewModel mViewModel;

    public SelectGroupDialogFragment(IGroupListener iGroupListener) {
        this.iGroupListener = iGroupListener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Init dialog
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_group_select, null);
        dialog.setContentView(viewDialog);
        // Init UI and Behaviour
        mViewModel = new ViewModelProvider(this).get(SelectGroupDialogViewModel.class);
        RecyclerView groupRecyclerView = dialog.findViewById(R.id.group_select_rcv);
        GroupAdapter groupAdapter = new GroupAdapter((taskGroup, position) -> {
            iGroupListener.onClick(taskGroup, position);
            dismiss();
        });
        if (groupRecyclerView != null) {
            groupRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            groupRecyclerView.setAdapter(groupAdapter);
        }
        mViewModel.getGroupList().observe(this, taskGroups -> groupAdapter.submitList(taskGroups));
        return dialog;
    }
}
