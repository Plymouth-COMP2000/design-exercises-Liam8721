package com.example.comp2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestMakeBooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestMakeBooking extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestMakeBooking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestMakeBooking.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestMakeBooking newInstance(String param1, String param2) {
        GuestMakeBooking fragment = new GuestMakeBooking();
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

        View view = inflater.inflate(R.layout.fragment_guest_make_booking, container, false);

        ImageButton GuestMakeBookingBackIC = view.findViewById(R.id.GuestMakeBookingBackIC);

        Spinner SpinnerGuestMakeBookingDate = view.findViewById(R.id.GuestMakeBookingDate);
        Spinner SpinnerGuestMakeBookingPartySize = view.findViewById(R.id.GuestMakeBookingPartySize);
        Spinner SpinnerGuestMakeBookingTime = view.findViewById(R.id.GuestMakeBookingTime);


        GuestMakeBookingBackIC.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestMakeBooking_to_guestHome);
        });

        // Setup all spinners using the helper method
        setupSpinner(SpinnerGuestMakeBookingDate, R.array.GuestBookingDate);
        setupSpinner(SpinnerGuestMakeBookingPartySize, R.array.GuestBookingParty);
        setupSpinner(SpinnerGuestMakeBookingTime, R.array.GuestBookingTime);


        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Helper method to setup a spinner with custom layouts
     * @param spinner Spinner view to configure
     * @param spinnerItems String array resource ID (from res/values/strings.xml)
     */
    private void setupSpinner(Spinner spinner, int spinnerItems) {
        // Load string array from resources
        String[] items = getResources().getStringArray(spinnerItems);

        // Create adapter with closed view layout (white text)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.guest_make_booking_spinner_closed_layout,
                items
        );

        // Set dropdown layout (black text)
        adapter.setDropDownViewResource(R.layout.guest_make_booking_spinner_dropdown_layout);

        // Apply adapter to spinner
        spinner.setAdapter(adapter);
    }
}