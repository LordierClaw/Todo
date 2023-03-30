package com.lordierclaw.todo.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.model.User;
import com.lordierclaw.todo.viewmodel.SettingsViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel mViewModel;

    private Preference accountLog, accountSync, accountChangePfp;
    private SwitchPreference darkModeSwitch;
    private Preference generateDummyData, deleteAllTask, clearData;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        initUI();
        initBehaviour();
    }

    void initUI() {
        accountLog = findPreference(getResources().getString(R.string.key_account_log));
        accountSync = findPreference(getResources().getString(R.string.key_account_sync));
        accountChangePfp = findPreference(getResources().getString(R.string.key_account_change_pfp));
        darkModeSwitch = findPreference(getResources().getString(R.string.key_dark_mode));
        generateDummyData = findPreference(getResources().getString(R.string.key_generate_dummy_data));
        deleteAllTask = findPreference(getResources().getString(R.string.key_delete_all_task));
        clearData = findPreference(getResources().getString(R.string.key_clear_data));
    }

    void initBehaviour() {
        // Set Default Value
        if (User.getInstance().isLocal()) {
            accountLog.setTitle(getResources().getString(R.string.settings_account_login));
            accountLog.setSummary(getResources().getString(R.string.settings_local_account_summary));
            accountSync.setEnabled(false);
        } else {
            accountLog.setTitle(getResources().getString(R.string.settings_account_logout));
            accountLog.setSummary("Logged as " + User.getInstance().getEmail());
            accountLog.setEnabled(true);
        }
        darkModeSwitch.setChecked(mViewModel.isDarkMode());
        // Event
        if (User.getInstance().isLocal()) accountLog.setOnPreferenceClickListener(preference -> mViewModel.logIn());
        else accountLog.setOnPreferenceClickListener(preference -> mViewModel.logOut());
        accountSync.setOnPreferenceClickListener(preference -> mViewModel.sync());
        darkModeSwitch.setOnPreferenceClickListener(preference -> darkModeOnClick());
        generateDummyData.setOnPreferenceClickListener(preference -> mViewModel.generateDummyData());
        deleteAllTask.setOnPreferenceClickListener(preference -> mViewModel.deleteAllTasks());
        clearData.setOnPreferenceClickListener(preference -> mViewModel.clearData());
    }

    private boolean darkModeOnClick() {
        if (darkModeSwitch.isChecked()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requireActivity().recreate();
        mViewModel.setDarkMode(darkModeSwitch.isChecked());
        return true;
    }
}