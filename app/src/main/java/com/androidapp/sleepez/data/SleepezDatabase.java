package com.androidapp.sleepez.data;

import android.content.Context;

import com.androidapp.sleepez.data.dao.AlarmDao;
import com.androidapp.sleepez.model.Alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class SleepezDatabase extends RoomDatabase {
    private static volatile SleepezDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract AlarmDao alarmDao();

    public static SleepezDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (SleepezDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SleepezDatabase.class, "SleepezDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
