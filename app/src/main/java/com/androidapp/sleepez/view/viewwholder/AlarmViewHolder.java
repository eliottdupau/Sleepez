package com.androidapp.sleepez.view.viewwholder;

import android.view.View;

import com.androidapp.sleepez.databinding.AlarmItemBinding;
import com.androidapp.sleepez.model.Alarm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private AlarmItemBinding binding;


    public AlarmViewHolder(AlarmItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bind(Alarm alarm) {
        binding.setAlarm(alarm);
        binding.executePendingBindings();
    }
}
