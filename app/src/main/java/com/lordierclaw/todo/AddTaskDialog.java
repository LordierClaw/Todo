package com.lordierclaw.todo;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AddTaskDialog extends BottomSheetDialog {

    View viewDialog;
    EditText newTaskText;
    Button newTaskButton;

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
        viewDialog = getLayoutInflater().inflate(R.layout.new_task, null);
        this.setContentView(viewDialog);
        newTaskText = viewDialog.findViewById(R.id.newTaskText);
        newTaskButton = viewDialog.findViewById(R.id.newTaskButton);
    }

    private void initBehaviour() {
        // Resize dialog when keyboard is shown
        Window window = getWindow();
        if (window != null) window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //Add Task
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskButtonOnClick();
            }
        });
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
    }

    private void newTaskButtonOnClick() {
        //TODO: add Task to data
        dismiss();
    }
}
