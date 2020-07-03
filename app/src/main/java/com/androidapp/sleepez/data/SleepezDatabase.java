package com.androidapp.sleepez.data;

import android.content.ContentValues;
import android.content.Context;

import com.androidapp.sleepez.data.dao.AlarmDao;
import com.androidapp.sleepez.model.Alarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues alarmValue = new ContentValues();
                alarmValue.put("alarmId", 1);
                alarmValue.put("name", "Travail");
                alarmValue.put("schedule", 800);
                alarmValue.put("day", 2);
                alarmValue.put("urlRingtone", "randomUrl");

                ContentValues alarmValue2 = new ContentValues();
                alarmValue2.put("alarmId", 2);
                alarmValue2.put("name", "Week End");
                alarmValue2.put("schedule", 800);
                alarmValue2.put("day", 2);
                alarmValue2.put("urlRingtone", "randomUrl");

                db.insert("Alarm", OnConflictStrategy.IGNORE, alarmValue);
                db.insert("Alarm", OnConflictStrategy.IGNORE, alarmValue2);
            }
        };
    }
}
