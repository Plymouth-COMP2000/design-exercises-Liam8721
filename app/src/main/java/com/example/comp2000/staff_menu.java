package com.example.comp2000;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class staff_menu extends Fragment {

    private RecyclerView recyclerView;
    private StaffMenuAdapter adapter;
    private List<MenuItem> menuItems;

    public staff_menu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.staffMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create sample data until the database is ready.
        // I'm using the 'salad.png' drawable you recently added.
        menuItems = new ArrayList<>();
        Drawable placeholderImage = ContextCompat.getDrawable(requireContext(), R.drawable.salad);
        menuItems.add(new MenuItem("Classic Burger", 12.99f, placeholderImage));
        menuItems.add(new MenuItem("Margherita Pizza", 14.50f, placeholderImage));
        menuItems.add(new MenuItem("Caesar Salad", 9.75f, placeholderImage));
        menuItems.add(new MenuItem("Chocolate Fudge Cake", 6.50f, placeholderImage));

        adapter = new StaffMenuAdapter(menuItems);
        recyclerView.setAdapter(adapter);
    }
}
