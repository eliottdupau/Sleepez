package com.androidapp.sleepez.model;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm implements Serializable {
    @PrimaryKey
    @NonNull
    public String alarmId;

    public String name;
    public int hour;
    public int minute;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;
    public boolean sunday;
    public String uriRingtone;
    public String strRingtone;
    public boolean isActivated;

    public Alarm() {
        this.alarmId = UUID.randomUUID().toString();
        this.hour = getCurrentHour();
        this.minute = getCurrentMinute();
        this.monday = true;
        this.tuesday = true;
        this.wednesday = true;
        this.thursday = true;
        this.friday = true;
        this.uriRingtone = getDefaultRingtoneUri();
        this.isActivated = true;
    }

    public Alarm(String name, int hour, int minute, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, String uriRingtone, String strRingtone) {
        this.alarmId = UUID.randomUUID().toString();
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.uriRingtone = uriRingtone;
        this.strRingtone = strRingtone;
        this.isActivated = true;
    }

    // Return the default alarm uri of the phone
    public String getDefaultRingtoneUri() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString();
    }

    // Return the title of the default alarm
    public String getRingtoneTitle(Context context, String uri) {
        return RingtoneManager.getRingtone(context, Uri.parse(uri)).getTitle(context);
    }

    // Return current hour of the phone
    private int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    // Return current minute of the phone
    private int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    // Format schedule with hour and minute
    public String formatSchedule(int hour, int minute) {
        return formatHour(hour) + " : " + formatMinute(minute);
    }

    private String formatHour(int hour) {
        if (hour < 10 && hour > 0) return "0" + hour;
        else if (hour == 0) return "0" + hour;
        else return "" + hour;
    }

    private String formatMinute(int minute) {
        if (minute < 10 && minute > 0) return "0" + minute;
        else if (minute == 0) return "0" + minute;
        else return "" + minute;
    }

    // Return a tab of boolean that contains the state (if alarm is activated) for each day
    public Boolean[] getDaysState() {
        return new Boolean[] {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
    }
}
