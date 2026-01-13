package com.example.comp2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;


import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;

import com.example.comp2000.notifications.NotificationHelper;


public class GuestMakeBooking extends Fragment {

    private BookingDBHelper dbHelper;

    public GuestMakeBooking() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new BookingDBHelper(requireContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guest_make_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("user_prefs", MODE_PRIVATE);

        String username = prefs.getString("logged_in_user", null);
        String email = prefs.getString("user_email", null);
        String fullName = username;

        ImageButton guestMakeBookingBackIC = view.findViewById(R.id.GuestMakeBookingBackIC);
        Spinner spinnerGuestMakeBookingDate = view.findViewById(R.id.GuestMakeBookingDate);
        Spinner spinnerGuestMakeBookingPartySize = view.findViewById(R.id.GuestMakeBookingPartySize);
        Spinner spinnerGuestMakeBookingTime = view.findViewById(R.id.GuestMakeBookingTime);
        Button searchButton = view.findViewById(R.id.GuestMakeBookingSearchButton);

        guestMakeBookingBackIC.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestMakeBooking_to_guestHome);
        });

        setupSpinner(spinnerGuestMakeBookingDate, R.array.GuestBookingDate);
        setupSpinner(spinnerGuestMakeBookingPartySize, R.array.GuestBookingParty);
        setupSpinner(spinnerGuestMakeBookingTime, R.array.GuestBookingTime);

        searchButton.setOnClickListener(v -> {
            String date = spinnerGuestMakeBookingDate.getSelectedItem().toString();
            String time = spinnerGuestMakeBookingTime.getSelectedItem().toString();
            String partySizeString = spinnerGuestMakeBookingPartySize.getSelectedItem().toString();

            int partySize = Integer.parseInt(partySizeString.replaceAll("[^0-9]", ""));

            Booking newBooking = new Booking(
                    date,
                    time,
                    partySize,
                    fullName,
                    username,
                    email,
                    "" // notes
            );

            boolean success = dbHelper.addBooking(newBooking);

            if (success) {
                Toast.makeText(getContext(), "Booking request sent!", Toast.LENGTH_SHORT).show();

                NotificationHelper.pushAlert(
                        requireContext(),
                        "Booking Created",
                        "Your booking for " + date + " at " + time + " was added.",
                        "booking"
                );

                Navigation.findNavController(v).navigate(R.id.action_guestMakeBooking_to_guestHome);
            } else {
                Toast.makeText(getContext(), "Failed to make booking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinner(Spinner spinner, int spinnerItems) {
        String[] items = getResources().getStringArray(spinnerItems);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.guest_make_booking_spinner_closed_layout,
                items
        );

        adapter.setDropDownViewResource(R.layout.guest_make_booking_spinner_dropdown_layout);

        spinner.setAdapter(adapter);
    }
}
