package com.aldacar.trackerlocation.ui.dashboard;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aldacar.trackerlocation.databinding.FragmentDashboardBinding;
import com.aldacar.trackerlocation.ui.LocationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationViewModel locationViewModel;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                if (fineLocationGranted == Boolean.TRUE || coarseLocationGranted == Boolean.TRUE) {
                    getLocation();
                } else {
                    Toast.makeText(requireContext(), "Se requieren los permisos para continuar", LENGTH_SHORT).show();
                }
            });

    public void requestLocationPermissions() {
        requestPermissionLauncher.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationViewModel = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button button = binding.locationButton;
        button.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermissions();
            } else {
                getLocation();
            }
        });

        return root;
    }

    public void getLocation() {
        if (getActivity() == null) return;
        Toast.makeText(requireContext(),"Obteniendo ubicación...",LENGTH_SHORT).show();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            new Geocoder(requireContext()).getFromLocation(lat,lng,1,addresses -> {
                                String cp = addresses.get(0).getPostalCode();
                                Log.v("cp", "cp:"+cp );
                                locationViewModel.insertLocation(lat, lng,cp);
                            });
                        }else {
                            locationViewModel.insertLocation(lat, lng,"");
                        }

                        Toast.makeText(requireContext(), "Ubicación guardada", LENGTH_SHORT).show();
                        Log.v("location", "Lat: " + lat + ", Lng: " + lng);
                    }
                })
                .addOnFailureListener(e -> Log.e("Location", "Error al obtener ubicación", e));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

