package com.aldacar.trackerlocation.db;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aldacar.trackerlocation.db.dao.LocationDao;
import com.aldacar.trackerlocation.db.entity.LocationEntity;

@Database(entities = {LocationEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "location_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
