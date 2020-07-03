package com.androidapp.sleepez.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm {
    @PrimaryKey
    @NonNull
    public String alarmId;

    public String name;
    public int schedule;
    public int day;
    public String urlRingtone;

    public Alarm() { }

    public Alarm(@NonNull String alarmId, String name, int schedule, int day, String urlRingtone) {
        this.alarmId = alarmId;
        this.name = name;
        this.schedule = schedule;
        this.day = day;
        this.urlRingtone = urlRingtone;
    }
}
