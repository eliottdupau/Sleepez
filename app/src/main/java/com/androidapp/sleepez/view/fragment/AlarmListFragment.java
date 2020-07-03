package com.androidapp.sleepez.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidapp.sleepez.R;
import com.androidapp.sleepez.databinding.FragmentAlarmListBinding;
import com.androidapp.sleepez.utils.ItemClickSupport;
import com.androidapp.sleepez.view.adapter.AlarmAdapter;
import com.androidapp.sleepez.viewmodel.AlarmViewModel;

public class AlarmListFragment extends Fragment {
    private FragmentAlarmListBinding binding;
    private AlarmViewModel viewModel;
    private AlarmAdapter adapter;

    public AlarmListFragment() {}

    public static AlarmListFragment newInstance() {
        return new AlarmListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlarmListBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        configureRecyclerView();
        configureItemClickListener();

        loadAlarms();

        return binding.getRoot();
    }

    private void configureRecyclerView() {
        adapter = new AlarmAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void configureItemClickListener() {
        ItemClickSupport.addTo(binding.recyclerView, R.layout.alarm_item).setOnItemClickListener((recyclerView, position, v) -> {
            // start alarm activity
            Toast.makeText(getContext(), "Alarm Activity", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadAlarms() {
        viewModel.loadAlarms().observe(getViewLifecycleOwner(), alarmList -> {
            adapter.updateData(alarmList);
        });
    }
}
