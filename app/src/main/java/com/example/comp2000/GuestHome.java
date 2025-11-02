package com.example.comp2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.widget.Toast;



public class GuestHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestHome() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GuestHome newInstance(String param1, String param2) {
        GuestHome fragment = new GuestHome();
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

        View view = inflater.inflate(R.layout.fragment_guest_home, container, false);


        Button guestMenuButton = view.findViewById(R.id.GuestMenuButton);
        Button GuestBookTableButton = view.findViewById(R.id.GuestBookTableButton);
        Button GuestViewBookingsButton = view.findViewById(R.id.GuestViewBookingsButton);

        guestMenuButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_guestMenu2);
        });

        GuestBookTableButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_guestMakeBooking);
        });

        GuestViewBookingsButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_myBookings);
        });

        return view;
    }
}