package com.aldacar.trackerlocation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aldacar.trackerlocation.db.entity.LocationEntity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private final List<LocationEntity> locationList;

    public LocationAdapter(List<LocationEntity> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationEntity location = locationList.get(position);
        String latLng = "Lat: " + location.latitude + ", Lng: " + location.longitude;
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(location.timestamp);
        holder.text1.setText(latLng);
        holder.text2.setText(String.format("%s%s", date, location.cp != null ? "CP:" + location.cp : ""));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}

