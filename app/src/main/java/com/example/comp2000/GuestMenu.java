package com.example.comp2000;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp2000.database.Category;
import com.example.comp2000.database.MenuDBHelper;

import java.util.ArrayList;
import java.util.List;

public class GuestMenu extends Fragment {

    private MenuDBHelper dbHelper;
    private RecyclerView recyclerView;
    private GuestMenuAdapter adapter;

    public GuestMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guest_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new MenuDBHelper(getContext());

        ImageButton backButton = view.findViewById(R.id.GuestMenuBackIC);
        backButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_guestMenu_to_guestHome);
        });

        recyclerView = view.findViewById(R.id.guestMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<Category> categories = dbHelper.getAllCategories();
        List<Object> combinedList = new ArrayList<>();

        for (Category category : categories) {
            combinedList.add(category);

            List<MenuItem> menuItems = dbHelper.getMenuItemsByCategory(category.getId());
            combinedList.addAll(menuItems);
        }

        adapter = new GuestMenuAdapter(combinedList);
        recyclerView.setAdapter(adapter);
    }
}
