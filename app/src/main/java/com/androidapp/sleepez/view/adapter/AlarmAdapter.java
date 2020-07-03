package com.androidapp.sleepez.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.androidapp.sleepez.databinding.AlarmItemBinding;
import com.androidapp.sleepez.model.Alarm;
import com.androidapp.sleepez.view.viewwholder.AlarmViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarmList;

    public AlarmAdapter() {
        this.alarmList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AlarmItemBinding binding = AlarmItemBinding.inflate(inflater, parent, false);

        return new AlarmViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.bind(alarmList.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public void updateData(List<Alarm> alarmList) {
        this.alarmList = alarmList;
        notifyDataSetChanged();
    }
}
