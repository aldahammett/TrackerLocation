package com.aldacar.trackerlocation.db.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aldacar.trackerlocation.db.entity.LocationEntity;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insert(LocationEntity location);

    @Query("SELECT * FROM locations ORDER BY timestamp DESC")
    List<LocationEntity> getAllLocations();
}