package com.androidapp.sleepez.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidapp.sleepez.R;
import com.androidapp.sleepez.databinding.ActivityUpdateAlarmBinding;
import com.androidapp.sleepez.databinding.DialogCustomLayoutLabelBinding;
import com.androidapp.sleepez.model.Alarm;
import com.androidapp.sleepez.viewmodel.AlarmViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateAlarmActivity extends AppCompatActivity {
    public static final int REQUEST_RINGTONE_ACTIVITY = 100;

    private ActivityUpdateAlarmBinding binding;
    private AlarmViewModel viewModel;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        configureToolbar();
        initAlarm();
        initUI();

        binding.setAlarm(alarm);

        binding.buttonSave.setOnClickListener(view -> {
            viewModel.updateAlarm(alarm);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.labelCard.setOnClickListener(view -> createAlertDialogForLabel());

        binding.ringtoneCard.setOnClickListener(view -> {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            startActivityForResult(intent, REQUEST_RINGTONE_ACTIVITY);
        });
    }

    /***********************
     **** Init Activity ****
     **********************/

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        viewModel.deleteAlarm(alarm);
        super.onBackPressed();
        return true;
    }

    private void initAlarm() {
        if (getIntent().getExtras() != null ) alarm = (Alarm) getIntent().getExtras().getSerializable("alarm");
    }

    private void initUI() {
        binding.timePicker.setIs24HourView(true);
        binding.timePicker.setCurrentHour(alarm.hour);
        binding.timePicker.setCurrentMinute(alarm.minute);

        setListenerToTimePicker();

        Chip[] chips = new Chip[] {binding.mondayChip, binding.tuesdayChip, binding.wednesdayChip,
                binding.thursdayChip, binding.fridayChip, binding.saturdayChip, binding.sundayChip};
        setListenerToChip(chips);
    }

    /***************************
     **** Manage User Input ****
     **************************/

    // Listen to time changes
    private void setListenerToTimePicker() {
        binding.timePicker.setOnTimeChangedListener((timePicker, hour, minute) -> {
            alarm.hour = hour;
            alarm.minute = minute;
        });
    }

    // Listen to the change of state of the chip
    private void setListenerToChip(Chip[] chips) {
        for (Chip chip : chips) {
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> manageActionForEachChip(chip, isChecked));
        }
    }

    // Add an action for each chip
    private void manageActionForEachChip(Chip chip, boolean isChecked) {
        switch (chip.getId()) {
            case R.id.mondayChip:
                alarm.monday = isChecked;
                break;
            case R.id.tuesdayChip:
                alarm.tuesday = isChecked;
                break;
            case R.id.wednesdayChip:
                alarm.wednesday = isChecked;
                break;
            case R.id.thursdayChip:
                alarm.thursday = isChecked;
                break;
            case R.id.fridayChip:
                alarm.friday = isChecked;
                break;
            case R.id.saturdayChip:
                alarm.saturday = isChecked;
                break;
            case R.id.sundayChip:
                alarm.sunday = isChecked;
                break;
            default:
                break;
        }
    }

    // Manage user input for label
    private void createAlertDialogForLabel() {
        DialogCustomLayoutLabelBinding dialogBinding = DialogCustomLayoutLabelBinding.inflate(getLayoutInflater());
        dialogBinding.setAlarm(alarm);
        View dialogView = dialogBinding.getRoot();
        TextInputEditText editText = dialogView.findViewById(R.id.labelInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                    String label = editText.getText() != null ? editText.getText().toString().trim() : getString(R.string.label);
                    alarm.name = label;
                    binding.label.setText(label.length() > 0 ? label : getString(R.string.label));
                })
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> { })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        handleResult(requestCode, resultCode, data);
    }

    // Manage result of Ringtone Picker
    private void handleResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri uri = Uri.parse(alarm.getDefaultRingtoneUri());
        String title = alarm.getRingtoneTitle(this, uri.toString());

        if(requestCode == REQUEST_RINGTONE_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                if (data != null) uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                title = RingtoneManager.getRingtone(this, uri).getTitle(this);
            }
        }

        alarm.uriRingtone = uri != null ? uri.toString() : alarm.getDefaultRingtoneUri();
        alarm.strRingtone = title;
        binding.ringtone.setText(title);
    }
}
