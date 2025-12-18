package com.example.comp2000;

import android.database.Cursor;
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

import com.example.comp2000.database.RestaurantDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MyBookings extends Fragment {

    public MyBookings() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        // Back button
        ImageButton backButton = view.findViewById(R.id.GuestMyBookingsBackButton);
        backButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_myBookings_to_guestHome)
        );

        // RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Load bookings from DB
        List<ReservationModels> bookings = loadBookingsFromDb();

        // Attach adapter
        BookingAdapter adapter = new BookingAdapter(bookings);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<ReservationModels> loadBookingsFromDb() {
        List<ReservationModels> bookings = new ArrayList<>();

        RestaurantDBHelper dbHelper = new RestaurantDBHelper(requireContext());
        Cursor cursor = dbHelper.getAllReservations();

        try {
            int idIndex = cursor.getColumnIndexOrThrow(RestaurantDBHelper.COL_RES_ID);
            int partyIndex = cursor.getColumnIndexOrThrow(RestaurantDBHelper.COL_PARTY_SIZE);
            int dateIndex = cursor.getColumnIndexOrThrow(RestaurantDBHelper.COL_DATE);
            int infoIndex = cursor.getColumnIndexOrThrow(RestaurantDBHelper.COL_ADDITIONAL_INFO);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                int partySize = cursor.getInt(partyIndex);
                String date = cursor.getString(dateIndex);
                String info = cursor.getString(infoIndex);

                bookings.add(new ReservationModels(id, partySize, date, info));
            }
        } finally {
            cursor.close();
        }

        return bookings;
    }
}
