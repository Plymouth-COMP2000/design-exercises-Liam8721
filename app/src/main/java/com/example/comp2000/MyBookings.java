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

import java.util.List;

public class MyBookings extends Fragment {

    private RecyclerView recyclerView;
    private MyBookingsAdapter adapter;
    private BookingDBHelper dbHelper;
    private List<Booking> bookingList;

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

        dbHelper = new BookingDBHelper(getContext());

        ImageButton backButton = view.findViewById(R.id.GuestMyBookingsBackButton);
        backButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_myBookings_to_guestHome)
        );

        recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        bookingList = dbHelper.getBookingsByGuestName("Guest User");

        adapter = new MyBookingsAdapter(bookingList);
        recyclerView.setAdapter(adapter);
    }
}
