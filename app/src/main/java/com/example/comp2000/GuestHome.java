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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GuestHome() {
    }

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
        Button GuestNotificationSettingsButton = view.findViewById(R.id.GuestNotificationSettingsButton);

        guestMenuButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_guestMenu2);
        });

        GuestBookTableButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_guestMakeBooking);
        });

        GuestViewBookingsButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_myBookings);
        });

        GuestNotificationSettingsButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestHome_to_notificationSettings);
        });

        return view;
    }
}