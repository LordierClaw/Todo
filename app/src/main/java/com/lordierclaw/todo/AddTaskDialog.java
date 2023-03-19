package com.lordierclaw.todo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.lordierclaw.todo.listener.IGroupListener;
import com.lordierclaw.todo.model.Manager;
import com.lordierclaw.todo.model.Task;

import java.util.Calendar;
import java.util.Date;

public class AddTaskDialog extends BottomSheetDialog {
    private EditText newTaskText;
    private Button newTaskButton;
    private MaterialButton selectGroupButton;
    private MaterialButton selectDateButton;
    private SelectGroupDialog selectGroupDialog;
    private Task.TaskGroup selectedGroup = Task.TaskGroup.None;
    private Date selectedDate = null;

    public AddTaskDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initBehaviour();
    }

    private void initUI() {
        View viewDialog = getLayoutInflater().inflate(R.layout.new_task, null);
        setContentView(viewDialog);
        newTaskText = viewDialog.findViewById(R.id.newTaskText);
        newTaskButton = viewDialog.findViewById(R.id.newTaskButton);
        selectGroupButton = viewDialog.findViewById(R.id.selectGroupButton);
        selectDateButton = viewDialog.findViewById(R.id.selectDateButton);
    }

    private void initBehaviour() {
        // Resize dialog when keyboard is shown
        Window window = getWindow();
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
        //Dismiss: clear content
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                reset();
            }
        });
        // Click Event
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskButtonOnClick();
            }
        });
        selectGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectGroupButtonOnClick();
            }
        });
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDateButtonOnClick();
            }
        });
    }
    private void setGroup(Task.TaskGroup group) {
        selectedGroup = group;
        if (group == Task.TaskGroup.None) selectGroupButton.setText(R.string.new_task_group_text);
        else selectGroupButton.setText(selectedGroup.toString());
    }

    private void setDate(Date date) {
        selectedDate = date;
        if (date == null) selectDateButton.setText(R.string.new_task_date_text);
        else selectDateButton.setText(Task.dateFormat.format(selectedDate));
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
                setDate(calendar.getTime());
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void selectGroupButtonOnClick() {
        selectGroupDialog = new SelectGroupDialog(getContext(), new IGroupListener() {
            @Override
            public void onClick(Task.TaskGroup taskGroup, int position) {
                setGroup(taskGroup);
                selectGroupDialog.dismiss();
            }
        });
        selectGroupDialog.show();
    }

    private void reset() {
        newTaskText.setText("");
        setGroup(Task.TaskGroup.None);
        setDate(null);
    }

    private void newTaskButtonOnClick() {
        Manager.getInstance().add(new Task(newTaskText.getText().toString(), selectedDate, selectedGroup));
        dismiss();
    }
}
