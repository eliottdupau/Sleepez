package com.androidapp.sleepez.view.viewwholder;

import android.content.res.Resources;
import android.view.View;

import com.androidapp.sleepez.R;
import com.androidapp.sleepez.databinding.AlarmItemBinding;
import com.androidapp.sleepez.model.Alarm;
import com.androidapp.sleepez.viewmodel.AlarmViewModel;
import com.google.android.material.textview.MaterialTextView;

import androidx.recyclerview.widget.RecyclerView;

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private AlarmItemBinding binding;
    private Resources resources;
    private MaterialTextView[] textViews;

    public AlarmViewHolder(AlarmItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
        resources = binding.getRoot().getResources();

        textViews = new MaterialTextView[]{binding.monday, binding.tuesday, binding.wednesday,
                binding.thursday, binding.friday, binding.saturday, binding.sunday};
    }

    public void bind(Alarm alarm, AlarmViewModel viewModel) {
        binding.setAlarm(alarm);
        binding.executePendingBindings();

        binding.alarmState.setChecked(binding.getAlarm().isActivated);
        if(alarm.name.length() == 0) binding.name.setVisibility(View.GONE);

        updateViewsWithAlarmState(alarm);
        registerListenerToSwitch(alarm, viewModel);
    }

    /*
     * Highlight or not the card according to if the alarm is activated or not
     * Happen when an item is initialize
     */
    private void updateViewsWithAlarmState(Alarm alarm) {
        if(binding.alarmState.isChecked()) enableAlarm(alarm);
        else disableAlarm();
    }

    /*
     * Highlight or not the card according to if the alarm is activated or not
     * Happen when user click on the switch to activate or not the alarm
     */
    private void registerListenerToSwitch(Alarm alarm, AlarmViewModel viewModel) {
        binding.alarmState.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (compoundButton.isChecked()) enableAlarm(alarm);
            else disableAlarm();
            updateAlarmState(viewModel, alarm, isChecked);
        });
    }

    // Highlight the card
    private void enableAlarm(Alarm alarm) {
        binding.name.setTextColor(resources.getColor(R.color.onSurface));
        binding.schedule.setTextColor(resources.getColor(R.color.onSurface));

        checkDaysState(alarm);
    }

    // Put off the card
    private void disableAlarm() {
        binding.name.setTextColor(resources.getColor(R.color.defaultColor));
        binding.schedule.setTextColor(resources.getColor(R.color.defaultColor));

        for (MaterialTextView textView : textViews) {
            textView.setTextColor(resources.getColor(R.color.defaultColor));
        }
    }

    // Change color of each day text view according to if the alarm is activated at this day
    private void checkDaysState(Alarm alarm) {
        int i = 0;
        for (boolean day : alarm.getDaysState()) {
            if (day) textViews[i].setTextColor(resources.getColor(R.color.colorAccent));
            else textViews[i].setTextColor(resources.getColor(R.color.defaultColor));
            i++;
        }
    }

    // Update alarm to activate or deactivate it in db
    private void updateAlarmState(AlarmViewModel viewModel, Alarm alarm, boolean isActivated) {
        alarm.isActivated = isActivated;
        viewModel.updateAlarm(alarm);
    }
}
