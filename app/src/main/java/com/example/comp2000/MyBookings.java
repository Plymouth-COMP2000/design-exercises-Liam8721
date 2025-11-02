package com.example.comp2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Spinner;


public class MyBookings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyBookings() {
    }

    // TODO: Rename and change types and number of parameters
    public static MyBookings newInstance(String param1, String param2) {
        MyBookings fragment = new MyBookings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        Spinner GuestBookingExample1 = view.findViewById(R.id.GuestBookingExample1);
        Spinner GuestBookingExample2 = view.findViewById(R.id.GuestBookingExample2);

        ImageButton GuestMyBookingsBackButton = view.findViewById(R.id.GuestMyBookingsBackButton);

        GuestMyBookingsBackButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_myBookings_to_guestHome);
        });

        setupSpinner(GuestBookingExample1, R.array.Table5);
        setupSpinner(GuestBookingExample2, R.array.Table6);

        return view;
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