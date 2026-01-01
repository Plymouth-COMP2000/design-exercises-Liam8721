package com.example.comp2000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Booking;

import java.util.List;

public class StaffReservationsAdapter extends RecyclerView.Adapter<StaffReservationsAdapter.ViewHolder> {

    private final List<Booking> bookingList;

    public StaffReservationsAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.partySize.setText("Party of " + booking.getPartySize());
        holder.date.setText(booking.getDate() + " at " + booking.getTime());
        holder.guestName.setText(booking.getGuestName());
        holder.additionalInfo.setText(booking.getNotes());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView partySize;
        final TextView date;
        final TextView guestName;
        final TextView additionalInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            partySize = itemView.findViewById(R.id.textPartySize);
            date = itemView.findViewById(R.id.textDate);
            guestName = itemView.findViewById(R.id.textGuestName);
            additionalInfo = itemView.findViewById(R.id.textAdditionalInfo);
        }
    }
}
