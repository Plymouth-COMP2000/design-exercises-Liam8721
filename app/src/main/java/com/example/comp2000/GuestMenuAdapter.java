package com.example.comp2000;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Category;

import java.util.List;
import java.util.Locale;

public class GuestMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CATEGORY = 0;
    private static final int TYPE_MENU_ITEM = 1;

    private final List<Object> combinedList;

    public GuestMenuAdapter(List<Object> combinedList) {
        this.combinedList = combinedList;
    }

    @Override
    public int getItemViewType(int position) {
        if (combinedList.get(position) instanceof Category) {
            return TYPE_CATEGORY;
        } else {
            return TYPE_MENU_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CATEGORY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_menu_category_header, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_menu_item, parent, false);
            return new MenuItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CATEGORY) {
            Category category = (Category) combinedList.get(position);
            ((CategoryViewHolder) holder).bind(category);
        } else {
            MenuItem menuItem = (MenuItem) combinedList.get(position);
            ((MenuItemViewHolder) holder).bind(menuItem);
        }
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryTitle;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryHeaderTitle);
        }

        void bind(Category category) {
            categoryTitle.setText(category.getName());
        }
    }

    static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView itemDescription;
        private final TextView itemPrice;

        MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.guestMenuItemImage);
            itemName = itemView.findViewById(R.id.guestMenuItemName);
            itemDescription = itemView.findViewById(R.id.guestMenuItemDescription);
            itemPrice = itemView.findViewById(R.id.guestMenuItemPrice);
        }

        void bind(MenuItem menuItem) {
            itemName.setText(menuItem.getName());
            itemDescription.setText(menuItem.getDescription());
            itemPrice.setText(String.format(Locale.UK, "£%.2f", menuItem.getPrice()));

            byte[] imageBlob = menuItem.getImageBlob();
            if (imageBlob != null && imageBlob.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
                itemImage.setImageBitmap(bitmap);
            } else {
                itemImage.setImageResource(R.drawable.ic_menu_button); // Placeholder
            }
        }
    }
}
