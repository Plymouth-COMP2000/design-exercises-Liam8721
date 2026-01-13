package com.example.comp2000;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Booking;
import com.example.comp2000.database.BookingDBHelper;
import com.example.comp2000.notifications.NotificationHelper;

import android.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.ViewHolder> {

    private final List<Booking> bookingList;
    private final BookingDBHelper dbHelper;
    private final Context context;

    public MyBookingsAdapter(Context context, List<Booking> bookingList) {
        this.bookingList = bookingList;
        this.context = context;
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

        holder.itemView.setOnClickListener(v -> showEditDialog(v.getContext(), booking, holder.getAdapterPosition()));

        holder.partySize.setText("Party of " + booking.getPartySize());
        holder.date.setText(booking.getDate() + " at " + booking.getTime());
        holder.guestName.setText(booking.getGuestName());

        String notes = booking.getNotes();
        holder.additionalInfo.setText(
                (notes == null || notes.trim().isEmpty()) ? "No additional notes" : notes
        );

        holder.cancelButton.setVisibility(View.VISIBLE);
        holder.cancelButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Cancel reservation?")
                    .setMessage("Are you sure you want to cancel this booking?")
                    .setPositiveButton("Cancel booking", (dialog, which) -> {
                        dbHelper.deleteBooking(booking.getId());

                        NotificationHelper.pushAlert(
                                context,
                                "Booking Cancelled",
                                "Your booking for " + booking.getDate() + " at " + booking.getTime() + " has been cancelled.",
                                "booking"
                        );

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

    private void showEditDialog(Context context, Booking booking, int adapterPos) {
        EditText partySizeET = new EditText(context);
        partySizeET.setInputType(InputType.TYPE_CLASS_NUMBER);
        partySizeET.setHint("Party size");
        partySizeET.setText(String.valueOf(booking.getPartySize()));

        EditText notesET = new EditText(context);
        notesET.setHint("Notes");
        notesET.setText(booking.getNotes() == null ? "" : booking.getNotes());

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (16 * context.getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);
        layout.addView(partySizeET);
        layout.addView(notesET);

        new AlertDialog.Builder(context)
                .setTitle("Edit booking")
                .setView(layout)
                .setPositiveButton("Save", (d, which) -> {
                    String partyStr = partySizeET.getText().toString().trim();
                    int newParty;
                    try {
                        newParty = Integer.parseInt(partyStr);
                    } catch (Exception e) {
                        newParty = booking.getPartySize();
                    }

                    booking.setPartySize(newParty);
                    booking.setNotes(notesET.getText().toString().trim());

                    dbHelper.updateBooking(booking);

                    NotificationHelper.pushAlert(
                            context,
                            "Booking Updated",
                            "Your booking for " + booking.getDate() + " at " + booking.getTime() + " has been updated.",
                            "booking"
                    );

                    if (adapterPos != RecyclerView.NO_POSITION) {
                        notifyItemChanged(adapterPos);
                    } else {
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
