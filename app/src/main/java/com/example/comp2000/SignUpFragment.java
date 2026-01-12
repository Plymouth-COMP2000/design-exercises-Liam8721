package com.example.comp2000;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.comp2000.api.VolleySingleton;

public class SignUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpBtn = view.findViewById(R.id.SignUpButton);
        Button signInBtn = view.findViewById(R.id.SignInButton);

        TextInputEditText usernameET = view.findViewById(R.id.SignUpUsernameEditText);
        TextInputEditText passwordET = view.findViewById(R.id.SignUpPasswordEditText);
        TextInputEditText firstNameET = view.findViewById(R.id.SignUpFirstNameEditText);
        TextInputEditText lastNameET = view.findViewById(R.id.SignUpLastNameEditText);
        TextInputEditText emailET = view.findViewById(R.id.SignUpEmailEditText);
        TextInputEditText phoneET = view.findViewById(R.id.SignUpPhoneEditText);

        signUpBtn.setOnClickListener(v -> {
            String username = text(usernameET);
            String password = text(passwordET);
            String firstname = text(firstNameET);
            String lastname = text(lastNameET);
            String email = text(emailET);
            String contact = text(phoneET);

            if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()
                    || email.isEmpty() || contact.isEmpty()) {
                Toast.makeText(requireContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject userJson = new JSONObject();
            try {
                userJson.put("username", username);
                userJson.put("password", password);
                userJson.put("firstname", firstname);
                userJson.put("lastname", lastname);
                userJson.put("email", email);
                userJson.put("contact", contact);
                String userType = determineUserType(email);
                userJson.put("usertype", userType);

            } catch (JSONException e) {
                Toast.makeText(requireContext(), "Failed to build request", Toast.LENGTH_SHORT).show();
                return;
            }

            VolleySingleton.createUser(requireContext(), userJson, response -> {
                Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_sign_up_to_login);
            }, error -> {
                int code = error.networkResponse == null ? -1 : error.networkResponse.statusCode;
                if (code == 400) {
                    Toast.makeText(requireContext(), "Invalid user data", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("SignUpFragment", "Volley error", error);
                    Toast.makeText(requireContext(), "Network/server error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        signInBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_sign_up_to_login)
        );

        return view;
    }

    private String text(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private String determineUserType(String email) {
        email = email.toLowerCase().trim();

        if (email.endsWith("@company.com")) {
            return "Staff";
        }

        return "GuestUser";
    }

}
