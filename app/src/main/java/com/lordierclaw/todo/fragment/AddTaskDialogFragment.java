package com.lordierclaw.todo.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.viewmodel.AddTaskDialogViewModel;

import java.util.Calendar;

public class AddTaskDialogFragment extends BottomSheetDialogFragment {
    // UI VARIABLE
    private EditText newTaskText;
    private Button newTaskButton;
    private MaterialButton selectGroupButton;
    private MaterialButton selectDateButton;

    // OTHER VARIABLE
    private AddTaskDialogViewModel mViewModel;
    private BottomSheetDialog mDialog;

    public AddTaskDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddTaskDialogViewModel.class);
        initUI();
        initBehaviour();
        return mDialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        reset();
    }

    private void initUI() {
        View viewDialog = getLayoutInflater().inflate(R.layout.new_task, null);
        mDialog.setContentView(viewDialog);
        newTaskText = viewDialog.findViewById(R.id.newTaskText);
        newTaskButton = viewDialog.findViewById(R.id.newTaskButton);
        selectGroupButton = viewDialog.findViewById(R.id.selectGroupButton);
        selectDateButton = viewDialog.findViewById(R.id.selectDateButton);
    }

    private void initBehaviour() {
        // Resize dialog when keyboard is shown
        Window window = mDialog.getWindow();
        if (window != null) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // EditText and AddButton Behaviour
        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newTaskButton.setEnabled(!newTaskText.getText().toString().equals(""));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        // Click Event
        newTaskButton.setOnClickListener(view -> {
            mViewModel.addTask(String.valueOf(newTaskText.getText()));
            dismiss();
        });
        selectGroupButton.setOnClickListener(view -> selectGroupButtonOnClick());
        selectDateButton.setOnClickListener(view -> selectDateButtonOnClick());
        mViewModel.getSelectedGroup().observe(this, taskGroup -> selectGroupButton.setText(mViewModel.getGroupString()));
        mViewModel.getSelectedDate().observe(this, date -> selectDateButton.setText(mViewModel.getDateString()));
    }

    private void selectGroupButtonOnClick() {
        SelectGroupDialogFragment selectGroupDialogFragment = new SelectGroupDialogFragment((taskGroup, position) -> {
            mViewModel.getSelectedGroup().setValue(taskGroup);
        });
        selectGroupDialogFragment.show(getParentFragmentManager(), selectGroupDialogFragment.getTag());
    }

    private void selectDateButtonOnClick() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                mViewModel.setDate(calendar.getTime());
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void reset() {
        newTaskText.setText("");
        mViewModel.resetData();
    }
}
