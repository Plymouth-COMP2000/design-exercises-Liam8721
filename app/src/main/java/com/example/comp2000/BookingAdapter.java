package com.example.comp2000;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final List<ReservationModels> bookingList;

    public BookingAdapter(List<ReservationModels> bookingList) {
        this.bookingList = bookingList;
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
        ReservationModels booking = bookingList.get(position);

        holder.textPartySize.setText("Party of " + booking.GetPartySize());
        holder.textDate.setText(booking.GetDate());

        String info = booking.GetAdditionalInfo();
        holder.textAdditionalInfo.setText(
                (info == null || info.trim().isEmpty())
                        ? "No additional notes"
                        : info
        );
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
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
