package com.lordierclaw.todo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.lordierclaw.todo.R;
import com.lordierclaw.todo.fragment.AboutFragment;
import com.lordierclaw.todo.fragment.AddTaskDialogFragment;
import com.lordierclaw.todo.fragment.ListTaskFragment;
import com.lordierclaw.todo.fragment.SettingsFragment;
import com.lordierclaw.todo.fragment.TaskDetailsDialogFragment;
import com.lordierclaw.todo.listener.ITaskLayoutListener;
import com.lordierclaw.todo.model.Task;

public class MainActivity extends AppCompatActivity {
    // UI VARIABLE
    private DrawerLayout mainDrawerLayout;
    private MaterialToolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton newTaskFloatButton;

    // OTHER VARIABLE
    private AddTaskDialogFragment addTaskDialog;
    private TaskDetailsDialogFragment taskDetailsDialog;
    private ListTaskFragment listTaskFragment;
    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private static final String MENU_ID_TAG = "navigation_menu_id";
    private int navigationDefaultId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            navigationDefaultId = savedInstanceState.getInt(MENU_ID_TAG);
            Log.d("onSave_recreate", String.valueOf(navigationDefaultId));
        }
        else navigationDefaultId = 0;
        setContentView(R.layout.activity_main);
        initUI();
        initBehaviour();
    }

    private void initUI() {
        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_nav_view);
        toolbar = findViewById(R.id.toolbar);
        newTaskFloatButton = findViewById(R.id.new_task_float_btn);
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
        navigationView.setNavigationItemSelectedListener(item -> navItemSelected(item));
        // List Task Event
        listTaskFragment.setTaskLayoutListener(new ITaskLayoutListener() {
            @Override
            public void onClick(Task task) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TASK_TO_SHOW_DETAILS", task);
                taskDetailsDialog.setArguments(bundle);
                taskDetailsDialog.show(getSupportFragmentManager(), taskDetailsDialog.getTag());
            }
        });
    }

    private boolean navItemSelected(@NonNull MenuItem item) {
        int groupId = item.getGroupId();
        int id = item.getItemId();
        navigationView.setCheckedItem(item);
        toolbar.setTitle(item.getTitle());
        if (groupId != R.id.nav_others) {
            newTaskFloatButton.show();
            replaceFragment(listTaskFragment);
            if (id == R.id.nav_my_day) {
                listTaskFragment.setListType();
            } else if (id == R.id.nav_task) {
                listTaskFragment.setListType(Task.TaskGroup.None);
            } else if (id == R.id.nav_group_home) {
                listTaskFragment.setListType(Task.TaskGroup.Home);
            } else if (id == R.id.nav_group_work) {
                listTaskFragment.setListType(Task.TaskGroup.Work);
            } else if (id == R.id.nav_group_education) {
                listTaskFragment.setListType(Task.TaskGroup.Education);
            } else if (id == R.id.nav_group_personal) {
                listTaskFragment.setListType(Task.TaskGroup.Personal);
            } else if (id == R.id.nav_group_college_club) {
                listTaskFragment.setListType(Task.TaskGroup.CollegeAndClub);
            }
        } else {
            newTaskFloatButton.hide();
            if (id == R.id.nav_about) {
                replaceFragment(aboutFragment);
            } else if (id == R.id.nav_settings) {
                replaceFragment(settingsFragment);
            }
        }
        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_view, fragment);
        transaction.commitNow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        navItemSelected(navigationView.getMenu().getItem(navigationDefaultId));
    }

    @Override
    public void onBackPressed() {
        if (mainDrawerLayout == null) return;
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) mainDrawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (navigationView.getCheckedItem() != null && navigationView.getCheckedItem().getItemId() == R.id.nav_settings) {
            outState.putInt(MENU_ID_TAG, 7);
        }
        super.onSaveInstanceState(outState);
    }
}