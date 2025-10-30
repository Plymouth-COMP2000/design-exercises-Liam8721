package com.example.comp2000;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    public MenuItemAdapter(@NonNull Context context, @NonNull List<MenuItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.menu_item, null);
        }
        TextView itemName = convertView.findViewById(R.id.ItemName);
        if (itemName != null) {
            itemName.setText(Objects.requireNonNull(getItem(0)).getName());
        }
        TextView itemPrice = convertView.findViewById(R.id.ItemPrice);
        if (itemPrice != null) {
            itemPrice.setText("");
        }
        return convertView;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(getContext(), R.layout.menu_item, null);
        if (position == 0) {
            TextView invisible = new TextView(getContext());
            invisible.setHeight(0);
            invisible.setVisibility(View.GONE);
            return invisible;
        }
        MenuItem menuItem = getItem(position);
        if (menuItem != null) {
            TextView itemName = convertView.findViewById(R.id.ItemName);
            if (itemName != null) {
                itemName.setText(menuItem.getName());
            }
            TextView itemPrice = convertView.findViewById(R.id.ItemPrice);
            if (itemPrice != null) {
                itemPrice.setText(String.format(Locale.getDefault(), "%.2f", menuItem.getPrice()));
            }
            ImageView itemImage = convertView.findViewById(R.id.ItemImage);
            if (itemImage != null) {
                itemImage.setImageDrawable(menuItem.getImage());
            }
        }
        return convertView;
    }
}