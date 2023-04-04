package com.lordierclaw.todo.fragment;

import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.viewmodel.GroupEditDialogViewModel;

public class GroupEditDialogFragment extends DialogFragment {
    // UI VARIABLE
    private View mView;
    private TextView mTitle;
    private EditText mEditText;
    private MaterialButton cancelBtn, acceptBtn;

    // OTHER VARIABLE
    private GroupEditDialogViewModel mViewModel;
    private boolean isEditDialog;

    public GroupEditDialogFragment(boolean isEditDialog) {
        this.isEditDialog = isEditDialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_group_edit, container, false);
        mViewModel = new ViewModelProvider(this).get(GroupEditDialogViewModel.class);
        initUI();
        setUIContent();
        initBehaviour();
        return mView;
    }

    private void initUI() {
        mTitle = mView.findViewById(R.id.group_dialog_title);
        mEditText = mView.findViewById(R.id.group_dialog_text);
        cancelBtn = mView.findViewById(R.id.group_dialog_cancel_btn);
        acceptBtn = mView.findViewById(R.id.group_dialog_accept_btn);
        //Set background to match theme
        TypedValue backgroundColor = new TypedValue();
        getContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorSurface, backgroundColor, true);
        mView.setBackgroundColor(backgroundColor.data);
    }

    private void setUIContent() {
        if (isEditDialog) {
            mTitle.setText(getString(R.string.group_dialog_title_edit));
            acceptBtn.setText(getString(R.string.group_dialog_accept_btn_text));
            mEditText.setHint("");
        } else {
            mTitle.setText(getString(R.string.group_dialog_title_add));
            acceptBtn.setText(getString(R.string.group_dialog_add_btn_text));
            mEditText.setHint(getString(R.string.group_dialog_add_hint));
        }
    }

    private void initBehaviour() {
        cancelBtn.setOnClickListener(view -> dismiss());
        if (isEditDialog) acceptBtn.setOnClickListener(view -> {
            mViewModel.updateGroup(String.valueOf(mEditText.getText()));
            dismiss();
        });
        else acceptBtn.setOnClickListener(view -> {
            mViewModel.insertNewGroup(new TaskGroup(String.valueOf(mEditText.getText())));
            dismiss();
        });
        if (isEditDialog) {
            mViewModel.bindGroup((TaskGroup) getArguments().get("GROUP_TO_SHOW"));
            mViewModel.getGroup().observe(this, taskGroup -> mEditText.setText(taskGroup.getName()));
        }
    }

    public void putTaskGroup(TaskGroup taskGroup) {
        if (isEditDialog) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("GROUP_TO_SHOW", taskGroup);
            this.setArguments(bundle);
        }
    }

    public boolean isEditDialog() {
        return isEditDialog;
    }

}