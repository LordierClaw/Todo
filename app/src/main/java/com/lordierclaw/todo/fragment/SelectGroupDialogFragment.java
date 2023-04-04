package com.lordierclaw.todo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.GroupAdapter;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.viewmodel.SelectGroupDialogViewModel;


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
        mViewModel = new ViewModelProvider(this).get(SelectGroupDialogViewModel.class);
        // RecyclerView UI and Behaviour
        RecyclerView groupRecyclerView = dialog.findViewById(R.id.group_select_rcv);
        GroupAdapter groupAdapter = new GroupAdapter((taskGroup, position) -> {
            iGroupListener.onClick(taskGroup, position);
            dismiss();
        });
        if (groupRecyclerView != null) {
            groupRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            groupRecyclerView.setAdapter(groupAdapter);
        }
        TextView noItemText = dialog.findViewById(R.id.group_select_no_item_text);
        mViewModel.getGroupList().observe(this, taskGroups -> {
            groupAdapter.submitList(taskGroups);
            if (taskGroups.size() != 0) {
                noItemText.setVisibility(View.GONE);
            } else {
                noItemText.setVisibility(View.VISIBLE);
            }
        });
        // Add Group Button UI and Behaviour
        ImageView addGroupBtn = dialog.findViewById(R.id.group_select_add_btn);
        addGroupBtn.setOnClickListener(view -> {
            GroupEditDialogFragment dialogFragment = new GroupEditDialogFragment(false);
            dialogFragment.show(getParentFragmentManager(), dialogFragment.getTag());
        });
        return dialog;
    }
}
