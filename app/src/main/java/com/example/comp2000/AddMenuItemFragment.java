package com.example.comp2000;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comp2000.database.Category;
import com.example.comp2000.database.MenuDBHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AddMenuItemFragment extends Fragment {

    private MenuDBHelper dbHelper;
    private ImageView itemImageView;
    private TextInputEditText nameEditText, descEditText, priceEditText;
    private AutoCompleteTextView categoryAutoComplete;
    private Button saveItemButton;

    private List<Category> categoryList;
    private byte[] imageBlob;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        Bitmap originalBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().getContentResolver(), imageUri));
                        Bitmap resizedBitmap = resizeBitmap(originalBitmap, 800, 800);

                        itemImageView.setImageBitmap(resizedBitmap);
                        
                        // Convert the resized bitmap to a compressed byte array for DB
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
                        imageBlob = stream.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbHelper = new MenuDBHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_menu_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemImageView = view.findViewById(R.id.itemImageView);
        nameEditText = view.findViewById(R.id.itemNameEditText);
        descEditText = view.findViewById(R.id.itemDescEditText);
        priceEditText = view.findViewById(R.id.itemPriceEditText);
        categoryAutoComplete = view.findViewById(R.id.categoryAutoComplete);
        saveItemButton = view.findViewById(R.id.saveItemButton);

        itemImageView.setOnClickListener(v -> openImagePicker());
        saveItemButton.setOnClickListener(v -> saveMenuItem());

        populateCategorySpinner();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void populateCategorySpinner() {
        categoryList = dbHelper.getAllCategories();
        List<String> categoryNames = categoryList.stream().map(Category::getName).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryNames);
        categoryAutoComplete.setAdapter(adapter);
    }

    private void saveMenuItem() {
        String name = nameEditText.getText().toString().trim();
        String description = descEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String categoryName = categoryAutoComplete.getText().toString();

        if (name.isEmpty() || priceStr.isEmpty() || categoryName.isEmpty() || imageBlob == null) {
            Toast.makeText(getContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        float price = Float.parseFloat(priceStr);
        int categoryId = getCategoryIdByName(categoryName);

        if (categoryId != -1) {
            MenuItem newItem = new MenuItem(name, description, price, imageBlob, categoryId);
            boolean success = dbHelper.addMenuItem(newItem);
            if (success) {
                Toast.makeText(getContext(), "Menu item saved", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).popBackStack();
            } else {
                Toast.makeText(getContext(), "Failed to save item", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Invalid category selected", Toast.LENGTH_SHORT).show();
        }
    }

    private int getCategoryIdByName(String name) {
        for (Category category : categoryList) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category.getId();
            }
        }
        return -1;
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        if (width <= maxWidth && height <= maxHeight) {
            return bitmap;
        }

        float ratio = (float) width / (float) height;
        if (width > height) {
            width = maxWidth;
            height = (int) (width / ratio);
        } else {
            height = maxHeight;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
