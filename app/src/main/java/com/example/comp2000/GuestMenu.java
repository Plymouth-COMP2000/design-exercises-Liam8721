package com.example.comp2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestMenu newInstance(String param1, String param2) {
        GuestMenu fragment = new GuestMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ArrayList<MenuItem> testStartersItems() {
        ArrayList<MenuItem> testItems = new ArrayList<>();
        testItems.add(new MenuItem("Starters", 0.0F, null));
        testItems.add(new MenuItem("Salad", 8.99F, null));
        testItems.add(new MenuItem("Soup", 6.99F, null));
        testItems.add(new MenuItem("Toast", 3.99F, null));
        return testItems;
    }

    private ArrayList<MenuItem> testMainsItems() {
        ArrayList<MenuItem> testItems = new ArrayList<>();
        testItems.add(new MenuItem("Mains", 0.0F, null));
        testItems.add(new MenuItem("Pizza", 10.99F, null));
        testItems.add(new MenuItem("Pasta", 12.99F, null));
        testItems.add(new MenuItem("Salad", 8.99F, null));
        return testItems;
    }

    private ArrayList<MenuItem> testDessertsItems() {
        ArrayList<MenuItem> testItems = new ArrayList<>();
        testItems.add(new MenuItem("Desserts", 0.0F, null));
        testItems.add(new MenuItem("Ice Cream", 4.99F, null));
        testItems.add(new MenuItem("Pie", 5.99F, null));
        testItems.add(new MenuItem("Cake", 6.99F, null));
        return testItems;
    }

    private ArrayList<MenuItem> testDrinksItems() {
        ArrayList<MenuItem> testItems = new ArrayList<>();
        testItems.add(new MenuItem("Drinks", 0.0F, null));
        testItems.add(new MenuItem("Soft Drinks", 2.99F, null));
        testItems.add(new MenuItem("Water", 1.99F, null));
        testItems.add(new MenuItem("Juice", 3.99F, null));
        return testItems;
    }

    private ArrayList<MenuItem> testSidesItems() {
        ArrayList<MenuItem> testItems = new ArrayList<>();
        testItems.add(new MenuItem("Sides", 0.0F, null));
        testItems.add(new MenuItem("Wings", 8.99F, null));
        testItems.add(new MenuItem("Fruit", 6.99F, null));
        testItems.add(new MenuItem("Cheese Sticks", 3.99F, null));
        return testItems;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_menu, container, false);

        Spinner StartersSpinner = view.findViewById(R.id.GuestMenuStarters);
            MenuItemAdapter StarterItemAdapter = new MenuItemAdapter(requireContext(), testStartersItems());
        StartersSpinner.setAdapter(StarterItemAdapter);
        StartersSpinner.setPrompt("Starter");
        StartersSpinner.setOnItemSelectedListener(null);

        Spinner MainsSpinner = view.findViewById(R.id.GuestMenuMain);
        MenuItemAdapter MainItemAdapter = new MenuItemAdapter(requireContext(), testMainsItems());
        MainsSpinner.setAdapter(MainItemAdapter);
        MainsSpinner.setPrompt("Mains");
        MainsSpinner.setOnItemSelectedListener(null);
        
        Spinner DessertsSpinner = view.findViewById(R.id.GuestMenuDesserts);
        MenuItemAdapter DessertsAdapter = new MenuItemAdapter(requireContext(), testDessertsItems());
        DessertsSpinner.setAdapter(DessertsAdapter);
        DessertsSpinner.setPrompt("Desserts");
        DessertsSpinner.setOnItemSelectedListener(null);
        
        Spinner DrinksSpinner = view.findViewById(R.id.GuestMenuDrinks);
        MenuItemAdapter DrinksAdapter = new MenuItemAdapter(requireContext(), testDrinksItems());
        DrinksSpinner.setAdapter(DrinksAdapter);
        DrinksSpinner.setPrompt("Drinks");
        DrinksSpinner.setOnItemSelectedListener(null);
        
        Spinner SidesSpinner = view.findViewById(R.id.GuestMenuSides);
        MenuItemAdapter SidesAdapter = new MenuItemAdapter(requireContext(), testSidesItems());
        SidesSpinner.setAdapter(SidesAdapter);
        SidesSpinner.setPrompt("Sides");
        SidesSpinner.setOnItemSelectedListener(null);

        ImageButton GuestMenuBackIC = view.findViewById(R.id.GuestMenuBackIC);
        GuestMenuBackIC.setOnClickListener(v -> {
            // Handle book table click
            Navigation.findNavController(v).navigate(R.id.action_guestMenu_to_guestHome);
        });


        return view;
    }
}