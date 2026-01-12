package com.example.comp2000;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.comp2000.api.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Login() {
    }


    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginBtn = view.findViewById(R.id.LoginSignInButton);
        Button registerBtn = view.findViewById(R.id.LoginRegisterButton);

        TextInputEditText usernameET = view.findViewById(R.id.LoginInputUsername);
        TextInputEditText passwordET = view.findViewById(R.id.textInputEditText);

        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE);

        if (!prefs.getBoolean("db_initialized", false)) {
            VolleySingleton.createStudentDb(requireContext(), r -> {
                prefs.edit().putBoolean("db_initialized", true).apply();
                Log.d("Login", "Student DB initialised");
            }, e -> {
                Log.e("Login", "Student DB init failed (may already exist)", e);
            });
        }


        loginBtn.setOnClickListener(v -> {
            String username = usernameET.getText() == null ? "" : usernameET.getText().toString().trim();
            String password = passwordET.getText() == null ? "" : passwordET.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }

            VolleySingleton.getUser(requireContext(), username, response -> {
                try {
                    JSONObject user = response.getJSONObject("user");
                    String apiPassword = user.optString("password", "");

                    if (apiPassword.equals(password)) {
                        prefs.edit()
                                .putString("logged_in_user", username)
                                .putString("user_type", user.optString("usertype", "student"))
                                .apply();

                        Navigation.findNavController(view).navigate(R.id.action_login_to_guestHome2);
                    } else {
                        Toast.makeText(requireContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("Login", "Bad JSON", e);
                    Toast.makeText(requireContext(), "Unexpected server response", Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                int code = error.networkResponse == null ? -1 : error.networkResponse.statusCode;
                if (code == 404) {
                    Toast.makeText(requireContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Login", "Volley error", error);
                    Toast.makeText(requireContext(), "Network/server error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        registerBtn.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_login_to_sign_up)
        );

        return view;
    }
}