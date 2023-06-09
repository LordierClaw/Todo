package com.lordierclaw.todo.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.TaskDetailsDialogViewModel;
import com.lordierclaw.todo.utils.TaskCalendar;

import java.util.Date;

public class TaskDetailsDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetDialog mDialog;
    private TaskDetailsDialogViewModel mViewModel;
    private EditText taskHeader;
    private TextView groupText, dateText;
    private ImageView groupRemoveButton, dateRemoveButton, taskDeleteButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TaskDetailsDialogViewModel.class);
        initUI();
        getTask();
        initBehaviour();
        return mDialog;
    }

    private void getTask() {
        if (getArguments() == null) return;
        mViewModel.getTask().setValue((Task) getArguments().get("TASK_TO_SHOW_DETAILS"));
    }

    private void initUI() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task_details, null);
        mDialog.setContentView(view);
        taskHeader = mDialog.findViewById(R.id.task_details_header);
        groupText = mDialog.findViewById(R.id.task_details_group_text);
        dateText = mDialog.findViewById(R.id.task_details_date_text);
        groupRemoveButton = mDialog.findViewById(R.id.task_details_group_remove_btn);
        dateRemoveButton = mDialog.findViewById(R.id.task_details_date_remove_btn);
        taskDeleteButton = mDialog.findViewById(R.id.task_details_delete_btn);
    }

    private void initBehaviour() {
        // Resize dialog when keyboard is shown
        Window window = mDialog.getWindow();
        if (window != null) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mViewModel.getTask().observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                if (task == null) return;
                taskHeader.setText(task.getName());
                groupText.setText(mViewModel.getGroupString());
                dateText.setText(mViewModel.getDateString());
            }
        });
        groupText.setOnClickListener(view -> selectGroupButtonOnClick());
        dateText.setOnClickListener(view -> selectDateButtonOnClick());
        groupRemoveButton.setOnClickListener(view -> mViewModel.setTaskGroup(null));
        dateRemoveButton.setOnClickListener(view -> mViewModel.setTaskDate(null));
        taskDeleteButton.setOnClickListener(view -> deleteTask());
    }

    public void putTask(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("TASK_TO_SHOW_DETAILS", task);
        this.setArguments(bundle);
    }

    private void deleteTask() {
        mViewModel.deleteTask();
        dismiss();
    }

    private void selectGroupButtonOnClick() {
        SelectGroupDialogFragment selectGroupDialogFragment = new SelectGroupDialogFragment((taskGroup, position) -> mViewModel.setTaskGroup(taskGroup));
        selectGroupDialogFragment.show(getParentFragmentManager(), selectGroupDialogFragment.getTag());
    }

    private void selectDateButtonOnClick() {
        int year, month, day;
        if (mViewModel.getTask().getValue() != null) {
            Date date = mViewModel.getTask().getValue().getDate();
            year = TaskCalendar.getYear(date);
            month = TaskCalendar.getMonth(date);
            day = TaskCalendar.getDay(date);
        } else {
            year = TaskCalendar.getCurrentYear();
            month = TaskCalendar.getCurrentMonth();
            day = TaskCalendar.getCurrentDay();
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            mViewModel.setTaskDate(TaskCalendar.getDate(y, m+1, d));
        }, year, month-1, day);
        datePickerDialog.show();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mViewModel.getTask().getValue() == null) return;
        mViewModel.setTaskName(taskHeader.getText().toString());
    }
}
