package com.example.comp2000;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;


import java.util.List;

public class MyBookings extends Fragment {

    private RecyclerView recyclerView;
    private MyBookingsAdapter adapter;
    private BookingDBHelper dbHelper;
    private List<Booking> bookingList;
    private String username;
    private String userType;

    public MyBookings() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("user_prefs", MODE_PRIVATE);

        username = prefs.getString("logged_in_user", null);
        userType = prefs.getString("user_type", "GuestUser");

        dbHelper = new BookingDBHelper(getContext());

        ImageButton backButton = view.findViewById(R.id.GuestMyBookingsBackButton);
        backButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_myBookings_to_guestHome)
        );

        recyclerView = view.findViewById(R.id.myBookingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        bookingList = new java.util.ArrayList<>();
        adapter = new MyBookingsAdapter(requireContext(), bookingList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dbHelper != null && recyclerView != null) {
            if ("Staff".equals(userType)) {
                bookingList = dbHelper.getAllBookings();
            } else {
                bookingList = dbHelper.getBookingsByUsername(username);
            }
            adapter = new MyBookingsAdapter(requireContext(), bookingList);
            recyclerView.setAdapter(adapter);
        }
    }
}
