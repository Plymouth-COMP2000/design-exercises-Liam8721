package com.example.comp2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StaffHome extends Fragment {

    public StaffHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button staffMenuButton = view.findViewById(R.id.StaffMenuButton);
        Button staffReservationsButton = view.findViewById(R.id.StaffReservationsButton);
        Button staffNotificationSettingsButton = view.findViewById(R.id.StaffNotificationSettingsButton);

        staffMenuButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(StaffHome.this)
                    .navigate(R.id.staff_menu);
        });

        staffReservationsButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(StaffHome.this)
                    .navigate(R.id.staff_reservations);
        });

        staffNotificationSettingsButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(StaffHome.this)
                    .navigate(R.id.action_staffHome2_to_notificationSettings);
        });
    }
}
