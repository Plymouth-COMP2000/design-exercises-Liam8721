package com.example.comp2000;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;

import java.util.List;

public class StaffReservationsAdapter extends RecyclerView.Adapter<StaffReservationsAdapter.ViewHolder> {

    private final List<Booking> bookingList;
    private final BookingDBHelper dbHelper;

    public StaffReservationsAdapter(Context context, List<Booking> bookingList) {
        this.bookingList = bookingList;
        this.dbHelper = new BookingDBHelper(context.getApplicationContext());
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
        holder.additionalInfo.setText(
                booking.getNotes() == null || booking.getNotes().trim().isEmpty()
                        ? "No additional notes"
                        : booking.getNotes()
        );

        holder.cancelButton.setVisibility(View.VISIBLE);
        holder.cancelButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Cancel booking?")
                    .setMessage("This will permanently remove the booking for " + booking.getGuestName() + ".")
                    .setPositiveButton("Cancel Booking", (dialog, which) -> {
                        dbHelper.deleteBooking(booking.getId());

                        int adapterPos = holder.getAdapterPosition();
                        if (adapterPos != RecyclerView.NO_POSITION) {
                            bookingList.remove(adapterPos);
                            notifyItemRemoved(adapterPos);
                        } else {
                            notifyDataSetChanged();
                        }

                        Toast.makeText(v.getContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Keep", null)
                    .show();
        });
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
        final Button cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            partySize = itemView.findViewById(R.id.textPartySize);
            date = itemView.findViewById(R.id.textDate);
            guestName = itemView.findViewById(R.id.textGuestName);
            additionalInfo = itemView.findViewById(R.id.textAdditionalInfo);
            cancelButton = itemView.findViewById(R.id.btnCancelBooking);
        }
    }
}
