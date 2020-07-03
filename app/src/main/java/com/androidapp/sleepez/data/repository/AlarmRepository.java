package com.androidapp.sleepez.data.repository;

import android.app.Application;

import com.androidapp.sleepez.data.SleepezDatabase;
import com.androidapp.sleepez.data.dao.AlarmDao;
import com.androidapp.sleepez.model.Alarm;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AlarmRepository {
    public AlarmDao alarmDao;

    private LiveData<List<Alarm>> alarmListLiveData;

    public AlarmRepository(Application application) {
        SleepezDatabase db = SleepezDatabase.getInstance(application);

        alarmDao = db.alarmDao();

        alarmListLiveData = alarmDao.loadAlarms();
    }

    public LiveData<List<Alarm>> loadAlarms() {
        return alarmListLiveData;
    }
}
