package com.lordierclaw.todo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.GroupAdapter;
import com.lordierclaw.todo.listener.IGroupListener;

public class SelectGroupDialogFragment extends BottomSheetDialogFragment {
    private IGroupListener iGroupListener;
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
        RecyclerView groupRecyclerView = dialog.findViewById(R.id.group_select_rcv);
        GroupAdapter groupAdapter = new GroupAdapter((taskGroup, position) -> {
            iGroupListener.onClick(taskGroup, position);
            dismiss();
        });
        if (groupRecyclerView != null) {
            groupRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            groupRecyclerView.setAdapter(groupAdapter);
        }
        return dialog;
    }
}
