package com.aldacar.trackerlocation.db.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public double latitude;
    public double longitude;
    public long timestamp;

    public String cp;

    public LocationEntity(double latitude, double longitude, long timestamp, String cp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.cp = cp;
    }
}
