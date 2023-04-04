package com.lordierclaw.todo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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
    private Preference deleteAllTask, clearData;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        initUI();
        initBehaviour();
    }

    private void initUI() {
        accountLog = findPreference(getResources().getString(R.string.key_account_log));
        accountSync = findPreference(getResources().getString(R.string.key_account_sync));
        accountChangePfp = findPreference(getResources().getString(R.string.key_account_change_pfp));
        darkModeSwitch = findPreference(getResources().getString(R.string.key_dark_mode));
        deleteAllTask = findPreference(getResources().getString(R.string.key_delete_all_task));
        clearData = findPreference(getResources().getString(R.string.key_clear_data));
    }

    private void initBehaviour() {
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
        if (User.getInstance().isLocal()) accountLog.setOnPreferenceClickListener(preference -> showNotAvailableMsg());
        else accountLog.setOnPreferenceClickListener(preference -> showNotAvailableMsg());
        accountSync.setOnPreferenceClickListener(preference -> showNotAvailableMsg());
        accountChangePfp.setOnPreferenceClickListener(preference -> showNotAvailableMsg());
        darkModeSwitch.setOnPreferenceClickListener(preference -> darkModeOnClick());
        deleteAllTask.setOnPreferenceClickListener(preference -> mViewModel.deleteAllTasks());
        clearData.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getContext(), "This application will close to clear data...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewModel.clearData();
                    getActivity().finish();
                    System.exit(0);
                }
            }, 2000);
            return true;
        });
    }

    private boolean showNotAvailableMsg() {
        Toast.makeText(getContext(), getString(R.string.settings_not_available), Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean darkModeOnClick() {
        if (darkModeSwitch.isChecked()) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        requireActivity().recreate();
        mViewModel.setDarkMode(darkModeSwitch.isChecked());
        return true;
    }
}