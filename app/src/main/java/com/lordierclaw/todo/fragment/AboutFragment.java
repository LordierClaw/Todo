package com.lordierclaw.todo.fragment;

import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lordierclaw.todo.R;

public class AboutFragment extends Fragment {
    public AboutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_about, container, false);
        TextView versionText = mView.findViewById(R.id.about_version_text);
        String version;
        try {
            PackageInfo pInfo = requireContext().getPackageManager().getPackageInfo(requireContext().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (Exception e) {
            version = "Unknown";
        }
        versionText.setText("Version: " + version);
        return mView;
    }
}