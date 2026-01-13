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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;
import com.example.comp2000.notifications.NotificationHelper;

public class StaffMakeBooking extends Fragment {

    private BookingDBHelper dbHelper;
    private EditText guestEmailEditText;
    private EditText guestUsernameEditText;
    private EditText guestNameEditText;
    private Spinner partySizeSpinner;
    private Spinner dateSpinner;
    private Spinner timeSpinner;
    private EditText notesEditText;

    public StaffMakeBooking() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new BookingDBHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff_make_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton backButton = view.findViewById(R.id.StaffMakeBookingBackIC);
        guestEmailEditText = view.findViewById(R.id.StaffMakeBookingGuestEmail);
        guestUsernameEditText = view.findViewById(R.id.StaffMakeBookingGuestUsername);
        guestNameEditText = view.findViewById(R.id.StaffMakeBookingGuestName);
        partySizeSpinner = view.findViewById(R.id.StaffMakeBookingPartySize);
        dateSpinner = view.findViewById(R.id.StaffMakeBookingDate);
        timeSpinner = view.findViewById(R.id.StaffMakeBookingTime);
        notesEditText = view.findViewById(R.id.StaffMakeBookingNotes);
        Button createButton = view.findViewById(R.id.StaffMakeBookingCreateButton);

        backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        setupSpinner(partySizeSpinner, R.array.GuestBookingParty);
        setupSpinner(dateSpinner, R.array.GuestBookingDate);
        setupSpinner(timeSpinner, R.array.GuestBookingTime);

        createButton.setOnClickListener(v -> {
            createBooking();
        });
    }

    private void createBooking() {
        String guestEmail = guestEmailEditText.getText().toString().trim();
        String guestUsername = guestUsernameEditText.getText().toString().trim();
        String guestName = guestNameEditText.getText().toString().trim();

        if (guestEmail.isEmpty() || guestUsername.isEmpty() || guestName.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all guest information fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!guestEmail.contains("@")) {
            Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = dateSpinner.getSelectedItem().toString();
        String time = timeSpinner.getSelectedItem().toString();
        String partySizeString = partySizeSpinner.getSelectedItem().toString();
        String notes = notesEditText.getText().toString();

        int partySize = 0;
        try {
            partySize = Integer.parseInt(partySizeString.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please select a valid party size", Toast.LENGTH_SHORT).show();
            return;
        }

        Booking newBooking = new Booking(
                date,
                time,
                partySize,
                guestName,
                guestUsername,
                guestEmail,
                notes
        );

        boolean success = dbHelper.addBooking(newBooking);

        if (success) {
            Toast.makeText(getContext(), "Booking created successfully!", Toast.LENGTH_SHORT).show();

            NotificationHelper.pushAlert(
                    requireContext(),
                    "Booking Created",
                    "Your booking for " + date + " at " + time + " has been created.",
                    "booking"
            );

            clearFields();

            Navigation.findNavController(getView()).navigateUp();
        } else {
            Toast.makeText(getContext(), "Failed to create booking", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        guestEmailEditText.setText("");
        guestUsernameEditText.setText("");
        guestNameEditText.setText("");
        notesEditText.setText("");
        partySizeSpinner.setSelection(0);
        dateSpinner.setSelection(0);
        timeSpinner.setSelection(0);
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

