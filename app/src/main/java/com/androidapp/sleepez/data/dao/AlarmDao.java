package com.androidapp.sleepez.data.dao;

import com.androidapp.sleepez.model.Alarm;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AlarmDao {

    @Query("Select * From Alarm")
    public LiveData<List<Alarm>> loadAlarms();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Alarm alarm);

    @Update
    public void update(Alarm alarm);

    @Delete
    public  void delete(Alarm alarm);
}
