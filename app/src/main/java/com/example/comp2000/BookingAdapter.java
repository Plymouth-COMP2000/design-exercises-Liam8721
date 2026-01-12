package com.example.comp2000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = (bookingList != null) ? bookingList : new ArrayList<>();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.textPartySize.setText("Party of " + booking.getPartySize());
        holder.textDate.setText(booking.getDate());

        String notes = booking.getNotes();
        holder.textAdditionalInfo.setText(
                (notes == null || notes.trim().isEmpty())
                        ? "No additional notes"
                        : notes
        );
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookings(List<Booking> newBookings) {
        bookingList.clear();
        if (newBookings != null) bookingList.addAll(newBookings);
        notifyDataSetChanged();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView textPartySize, textDate, textAdditionalInfo;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            textPartySize = itemView.findViewById(R.id.textPartySize);
            textDate = itemView.findViewById(R.id.textDate);
            textAdditionalInfo = itemView.findViewById(R.id.textAdditionalInfo);
        }
    }
}
