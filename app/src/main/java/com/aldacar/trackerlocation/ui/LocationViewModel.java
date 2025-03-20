package com.aldacar.trackerlocation.ui;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aldacar.trackerlocation.db.AppDatabase;
import com.aldacar.trackerlocation.db.dao.LocationDao;
import com.aldacar.trackerlocation.db.entity.LocationEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationViewModel extends AndroidViewModel {
    private final LocationDao locationDao;
    private final ExecutorService executorService;
    private final MutableLiveData<List<LocationEntity>> locationsLiveData = new MutableLiveData<>();

    public LocationViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        locationDao = db.locationDao();
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> locationsLiveData.postValue(locationDao.getAllLocations()));
    }

    public void insertLocation(double latitude, double longitude,String cp) {
        executorService.execute(() -> {
            locationDao.insert(new LocationEntity(latitude, longitude, System.currentTimeMillis(), cp));
            locationsLiveData.postValue(locationDao.getAllLocations());
        });
    }

    public LiveData<List<LocationEntity>> getAllLocations() {
        return locationsLiveData;
    }
}

