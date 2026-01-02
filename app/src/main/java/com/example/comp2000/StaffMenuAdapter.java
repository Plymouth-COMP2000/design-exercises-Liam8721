package com.example.comp2000;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class StaffMenuAdapter extends RecyclerView.Adapter<StaffMenuAdapter.ViewHolder> {

    private final List<MenuItem> menuItems;

    public StaffMenuAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.staff_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        holder.nameTextView.setText(menuItem.getName());
        holder.priceTextView.setText(String.format(Locale.UK, "£%.2f", menuItem.getPrice()));

        byte[] imageBlob = menuItem.getImageBlob();
        if (imageBlob != null && imageBlob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_menu_button);
        }
    }

    @Override
    public int getItemCount() {
        return menuItems != null ? menuItems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameTextView;
        final TextView priceTextView;
        final Button editButton;
        final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menuItemImageView);
            nameTextView = itemView.findViewById(R.id.menuItemName);
            priceTextView = itemView.findViewById(R.id.menuItemPrice);
            editButton = itemView.findViewById(R.id.editItemButton);
            deleteButton = itemView.findViewById(R.id.deleteItemButton);
        }
    }
}
