package com.lordierclaw.todo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.MenuAdapter;
import com.lordierclaw.todo.fragment.AboutFragment;
import com.lordierclaw.todo.fragment.AddTaskDialogFragment;
import com.lordierclaw.todo.fragment.ListTaskFragment;
import com.lordierclaw.todo.fragment.SettingsFragment;
import com.lordierclaw.todo.fragment.TaskDetailsDialogFragment;
import com.lordierclaw.todo.listener.IMenuListener;
import com.lordierclaw.todo.listener.ITaskLayoutListener;
import com.lordierclaw.todo.model.CustomMenuItem;
import com.lordierclaw.todo.model.Task;
import com.lordierclaw.todo.model.TaskGroup;
import com.lordierclaw.todo.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // UI VARIABLE
    private DrawerLayout mainDrawerLayout;
    private MaterialToolbar toolbar;
    private FloatingActionButton newTaskFloatButton;
    private MenuAdapter navAdapter;
    private RecyclerView navGroupRcv;

    // OTHER VARIABLE
    private AddTaskDialogFragment addTaskDialog;
    private TaskDetailsDialogFragment taskDetailsDialog;
    private ListTaskFragment listTaskFragment;
    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private MainViewModel mViewModel;
    private static final String MENU_ID_TAG = "navigation_menu_id";
    private int navigationDefaultId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if (savedInstanceState != null) {
            navigationDefaultId = savedInstanceState.getInt(MENU_ID_TAG);
        }
        else navigationDefaultId = 0;
        setContentView(R.layout.activity_main);
        initUI();
        initBehaviour();
    }

    private void initUI() {
        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        newTaskFloatButton = findViewById(R.id.new_task_float_btn);
        navAdapter = new MenuAdapter(this);
        navGroupRcv = findViewById(R.id.nav_group_rcv);
        addTaskDialog = new AddTaskDialogFragment();
        taskDetailsDialog = new TaskDetailsDialogFragment();
        listTaskFragment = new ListTaskFragment();
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
    }

    private void initBehaviour() {
        // Navigation Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Click Event
        newTaskFloatButton.setOnClickListener(view -> addTaskDialog.show(getSupportFragmentManager(), addTaskDialog.getTag()));
        // List Task Event
        listTaskFragment.setTaskLayoutListener(task -> sendTaskToDetailsDialog(task));
        // TaskGroup Menu
        navGroupRcv.setLayoutManager(new LinearLayoutManager(this));
        navGroupRcv.setAdapter(navAdapter);
        mViewModel.getGroupList().observe(this, taskGroups -> setMenuFromTaskGroup(taskGroups));
    }

    private void setMenuFromTaskGroup(List<TaskGroup> taskGroups) {
        if (taskGroups.size() == 0) {
            mViewModel.insertTaskGroup(new TaskGroup("Tasks"));
            return;
        }
        List<CustomMenuItem> itemList = new ArrayList<>();
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_my_day_24),"My Day", () -> {
            setFragmentView(listTaskFragment, "My Day");
            listTaskFragment.setListType();
        }));
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_home_24), "Tasks", () -> {
            setFragmentView(listTaskFragment, "Tasks");
            listTaskFragment.setListType(taskGroups.get(0));
        }));
        // TODO: Separated line
        for(int i = 1; i < taskGroups.size(); i++) {
            TaskGroup group = taskGroups.get(i);
            itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_bulleted_list_24), group.getName(), () -> {
                setFragmentView(listTaskFragment, group.getName());
                listTaskFragment.setListType(group);
            }));
        }
        // TODO: Separated line
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_settings_24), "Settings", () -> {
            setFragmentView(settingsFragment, "Settings");
        }));
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_about_24), "About", () -> {
            setFragmentView(aboutFragment, "About");
        }));
        navAdapter.submitList(itemList);
        //Close Drawer
    }

    private void sendTaskToDetailsDialog(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("TASK_TO_SHOW_DETAILS", task);
        taskDetailsDialog.setArguments(bundle);
        taskDetailsDialog.show(getSupportFragmentManager(), taskDetailsDialog.getTag());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_view, fragment);
        transaction.commitNow();
    }

    private void setFragmentView(Fragment fragment, String title) {
        replaceFragment(fragment);
        if (fragment.getClass().equals(ListTaskFragment.class)) newTaskFloatButton.show();
        else newTaskFloatButton.hide();
        if (toolbar != null) toolbar.setTitle(title);
        closeDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean closeDrawer() {
        if (mainDrawerLayout == null) return true;
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else return false;
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer()) super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        if (navigationView.getCheckedItem() != null && navigationView.getCheckedItem().getItemId() == R.id.nav_settings) {
//            outState.putInt(MENU_ID_TAG, 7);
//        }
        super.onSaveInstanceState(outState);
    }
}