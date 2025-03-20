package com.aldacar.trackerlocation.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aldacar.trackerlocation.R;
import com.aldacar.trackerlocation.adapter.LocationAdapter;
import com.aldacar.trackerlocation.ui.LocationViewModel;

public class HomeFragment extends Fragment {

    private LocationViewModel locationViewModel;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        locationViewModel.getAllLocations().observe(getViewLifecycleOwner(), locations -> {
            Log.v("locations", String.valueOf(locations.size()));
            adapter = new LocationAdapter(locations);
            recyclerView.setAdapter(adapter);
        });

        return view;
    }
}