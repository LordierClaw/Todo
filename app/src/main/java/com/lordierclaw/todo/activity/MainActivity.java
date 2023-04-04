package com.lordierclaw.todo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.adapter.MenuAdapter;
import com.lordierclaw.todo.fragment.AboutFragment;
import com.lordierclaw.todo.fragment.AddTaskDialogFragment;
import com.lordierclaw.todo.fragment.GroupEditDialogFragment;
import com.lordierclaw.todo.fragment.ListTaskFragment;
import com.lordierclaw.todo.fragment.SettingsFragment;
import com.lordierclaw.todo.fragment.TaskDetailsDialogFragment;
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

    // MENU VARIABLE
    private static final String MENU_ID_TAG = "navigation_menu_id";
    private int defaultSelectedMenu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) defaultSelectedMenu = savedInstanceState.getInt(MENU_ID_TAG);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
        setSupportActionBar(toolbar);
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
        List<CustomMenuItem> itemList = new ArrayList<>();
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_my_day_24), getString(R.string.nav_my_day), () -> {
            setFragmentView(listTaskFragment, 0);
            listTaskFragment.setListType();
            setUIFragment(listTaskFragment, getString(R.string.nav_my_day));
        }));
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_home_24), getString(R.string.nav_tasks), () -> {
            setFragmentView(listTaskFragment, 1);
            listTaskFragment.setListType(null);
            setUIFragment(listTaskFragment, getString(R.string.nav_tasks));
        }));
        itemList.add(new CustomMenuItem()); // DIVIDER LINE - position = 2
        for(int i = 0; i < taskGroups.size(); i++) {
            TaskGroup group = taskGroups.get(i);
            final int pos = 3+i;
            itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_bulleted_list_24), group.getName(), () -> {
                setFragmentView(listTaskFragment, pos);
                listTaskFragment.setListType(group);
                setUIFragment(listTaskFragment, group.getName());
            }));
        }
        int nextPos = taskGroups.size()+2;
        if (taskGroups.size() != 0) {
            itemList.add(new CustomMenuItem()); // DIVIDER LINE - position = nextPos+1
            nextPos += 1;
        }
        int finalNextPos = nextPos;
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_settings_24), getString(R.string.nav_settings), () -> {
            setFragmentView(settingsFragment, finalNextPos+1);
            setUIFragment(settingsFragment, getString(R.string.nav_settings));
        }));
        itemList.add(new CustomMenuItem(getDrawable(R.drawable.ic_about_24), getString(R.string.nav_about), () -> {
            setFragmentView(aboutFragment, finalNextPos+2);
            setUIFragment(aboutFragment, getString(R.string.nav_about));
        }));

        navAdapter.submitList(itemList);
        navGroupRcv.postDelayed(() -> {
            MenuAdapter.ItemViewHolder defaultSelectedItem = (MenuAdapter.ItemViewHolder) navGroupRcv.findViewHolderForAdapterPosition(defaultSelectedMenu);
            defaultSelectedItem.itemView.performClick();
        }, 100);
        /*
        Delay 100ms, wait till recyclerView ACTUALLY finish updating layout. post() function does not work as expected
        Yes, this is stupid but I can't get `ViewTreeObserver.OnGlobalLayoutListener` to work as I wanted
        Total time wasted: 4 hours
        */
    }

    private void sendTaskToDetailsDialog(Task task) {
        taskDetailsDialog.putTask(task);
        taskDetailsDialog.show(getSupportFragmentManager(), taskDetailsDialog.getTag());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_view, fragment);
        transaction.commitNow();
    }

    private void setFragmentView(Fragment fragment, int position) {
        closeDrawer();
        replaceFragment(fragment);
        defaultSelectedMenu = position;
    }

    private void setUIFragment(Fragment fragment, String title) {
        if (fragment.getClass().equals(ListTaskFragment.class)) {
            newTaskFloatButton.show();
            toolbar.getMenu().setGroupVisible(R.id.toolbar_overflow_group, listTaskFragment.getCurrentGroup() != null);
        } else {
            newTaskFloatButton.hide();
            toolbar.getMenu().setGroupVisible(R.id.toolbar_overflow_group, false);
        }
        if (toolbar != null) toolbar.setTitle(title);
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
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_edit_group) {
            GroupEditDialogFragment groupEditDialogFragment = new GroupEditDialogFragment(true);
            groupEditDialogFragment.putTaskGroup(listTaskFragment.getCurrentGroup());
            groupEditDialogFragment.show(getSupportFragmentManager(), groupEditDialogFragment.getTag());
            return true;
        } else if (id == R.id.toolbar_remove_group) {
            defaultSelectedMenu = 0;
            mViewModel.deleteGroup(listTaskFragment.getCurrentGroup());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(MENU_ID_TAG, defaultSelectedMenu);
        super.onSaveInstanceState(outState);
    }
}