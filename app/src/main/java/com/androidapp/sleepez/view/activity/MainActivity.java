package com.androidapp.sleepez.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.androidapp.sleepez.R;
import com.androidapp.sleepez.databinding.ActivityMainBinding;
import com.androidapp.sleepez.utils.ItemClickSupport;
import com.androidapp.sleepez.view.adapter.AlarmAdapter;
import com.androidapp.sleepez.viewmodel.AlarmViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AlarmViewModel viewModel;
    private AlarmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AlarmViewModel.class);

        configureRecyclerView();
        configureItemClickListener();
        loadAlarms();

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateAlarmActivity.class);
            startActivity(intent);
        });
    }

    private void configureRecyclerView() {
        adapter = new AlarmAdapter(viewModel);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void configureItemClickListener() {
        ItemClickSupport.addTo(binding.recyclerView, R.layout.alarm_item).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(this, UpdateAlarmActivity.class);
            intent.putExtra("alarm", adapter.getAlarm(position));
            startActivity(intent);
        });
    }

    // Get all alarms saved in the db and then notify the adapter
    private void loadAlarms() {
        viewModel.loadAlarms().observe(this, alarmList -> {
            adapter.updateData(alarmList);
        });
    }
}
