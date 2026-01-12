package com.example.comp2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;

import java.util.List;

public class staff_reservations extends Fragment {

    private RecyclerView recyclerView;
    private StaffReservationsAdapter adapter;
    private BookingDBHelper dbHelper;
    private List<Booking> bookingList;

    public staff_reservations() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new BookingDBHelper(getContext());

        recyclerView = view.findViewById(R.id.staffRecyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookingList = dbHelper.getAllBookings();

        adapter = new StaffReservationsAdapter(requireContext(), bookingList);
        recyclerView.setAdapter(adapter);
    }
}
