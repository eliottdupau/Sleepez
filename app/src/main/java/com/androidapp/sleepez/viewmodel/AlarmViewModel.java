package com.androidapp.sleepez.viewmodel;

import android.app.Application;

import com.androidapp.sleepez.data.repository.AlarmRepository;
import com.androidapp.sleepez.model.Alarm;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AlarmViewModel extends AndroidViewModel {
    private AlarmRepository repository;

    private LiveData<List<Alarm>> alarmListLiveData;

    public AlarmViewModel(@NonNull Application application) {
        super(application);

        repository = new AlarmRepository(application);
        alarmListLiveData = repository.loadAlarms();
    }

    public LiveData<List<Alarm>> loadAlarms() {
        return alarmListLiveData;
    }
}
