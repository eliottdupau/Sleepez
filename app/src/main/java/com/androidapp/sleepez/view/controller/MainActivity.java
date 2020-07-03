package com.androidapp.sleepez.view.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.androidapp.sleepez.R;
import com.androidapp.sleepez.databinding.ActivityMainBinding;
import com.androidapp.sleepez.view.fragment.AlarmListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, AlarmListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
