package com.example.comp2000;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class NotificationSettingsFragment extends Fragment {

    private SharedPreferences prefs;
    private Switch bookingCreatedToggle;
    private Switch bookingUpdatedToggle;
    private Switch bookingCancelledToggle;

    public NotificationSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = requireContext().getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton backButton = view.findViewById(R.id.notificationSettingsBackButton);
        bookingCreatedToggle = view.findViewById(R.id.bookingCreatedToggle);
        bookingUpdatedToggle = view.findViewById(R.id.bookingUpdatedToggle);
        bookingCancelledToggle = view.findViewById(R.id.bookingCancelledToggle);

        loadNotificationSettings();

        backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        bookingCreatedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveNotificationSetting("alerts_booking_created", isChecked);
            }
        });

        bookingUpdatedToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveNotificationSetting("alerts_booking_updated", isChecked);
            }
        });

        bookingCancelledToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveNotificationSetting("alerts_booking_cancelled", isChecked);
            }
        });
    }

    private void loadNotificationSettings() {
        bookingCreatedToggle.setChecked(prefs.getBoolean("alerts_booking_created", true));
        bookingUpdatedToggle.setChecked(prefs.getBoolean("alerts_booking_updated", true));
        bookingCancelledToggle.setChecked(prefs.getBoolean("alerts_booking_cancelled", true));
    }

    private void saveNotificationSetting(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}

