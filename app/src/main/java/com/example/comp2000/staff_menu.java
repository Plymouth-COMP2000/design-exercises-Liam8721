package com.example.comp2000;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Category;
import com.example.comp2000.database.MenuDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

public class staff_menu extends Fragment {

    private MenuDBHelper dbHelper;
    private RecyclerView recyclerView;
    private Spinner categorySpinner;
    private StaffMenuAdapter menuAdapter;
    private List<Category> categoryList;

    public staff_menu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new MenuDBHelper(getContext());

        recyclerView = view.findViewById(R.id.staffMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categorySpinner = view.findViewById(R.id.categorySpinner);

        ImageButton addCategoryButton = view.findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(v -> showAddCategoryDialog());

        FloatingActionButton addMenuItemFab = view.findViewById(R.id.addMenuItemFab);
        addMenuItemFab.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_staff_menu_to_addMenuItemFragment);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAndRefreshUI();
    }

    private void setupAndRefreshUI() {
        categoryList = dbHelper.getAllCategories();

        List<String> categoryNames = categoryList.stream().map(Category::getName).collect(Collectors.toList());
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < categoryList.size()) {
                    Category selectedCategory = categoryList.get(position);
                    updateRecyclerView(selectedCategory.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                updateRecyclerView(-1);
            }
        });

        if (!categoryList.isEmpty()) {
            int selectedPosition = categorySpinner.getSelectedItemPosition();
            if (selectedPosition != -1 && selectedPosition < categoryList.size()) {
                 updateRecyclerView(categoryList.get(selectedPosition).getId());
            }
        } else {
             updateRecyclerView(-1); // Clear list if no categories exist
        }
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Category");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String categoryName = input.getText().toString().trim();
            if (!categoryName.isEmpty()) {
                dbHelper.addCategory(new Category(categoryName));
                Toast.makeText(getContext(), "Category added", Toast.LENGTH_SHORT).show();
                setupAndRefreshUI();
            } else {
                Toast.makeText(getContext(), "Category name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateRecyclerView(int categoryId) {
        if (categoryId == -1) {
            recyclerView.setAdapter(new StaffMenuAdapter(null));
            return;
        }
        List<MenuItem> currentMenuItems = dbHelper.getMenuItemsByCategory(categoryId);
        menuAdapter = new StaffMenuAdapter(currentMenuItems);
        recyclerView.setAdapter(menuAdapter);
    }
}
