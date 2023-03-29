package com.lordierclaw.todo.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lordierclaw.todo.R;
import com.lordierclaw.todo.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        initUI();
        return mView;
    }

    void initUI() {

    }

}