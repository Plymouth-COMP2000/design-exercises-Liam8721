package com.example.comp2000.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Locale;

public class VolleySingleton {
    private static VolleySingleton instance;
    private final RequestQueue requestQueue;

    private static final String STUDENT_ID = "10919354";
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";

    private VolleySingleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) instance = new VolleySingleton(context);
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static void getUser(Context ctx, String username,
                               Response.Listener<JSONObject> callback,
                               Response.ErrorListener errorCallback) {
        String url = String.format(Locale.getDefault(), "%s/read_user/%s/%s", BASE_URL, STUDENT_ID, username);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, callback, errorCallback);
        getInstance(ctx).getRequestQueue().add(req);
    }

    public static void createUser(Context ctx, JSONObject userJson,
                                  Response.Listener<JSONObject> callback,
                                  Response.ErrorListener errorCallback) {
        String url = String.format(Locale.getDefault(), "%s/create_user/%s", BASE_URL, STUDENT_ID);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, userJson, callback, errorCallback);
        getInstance(ctx).getRequestQueue().add(req);
    }

    public static void createStudentDb(Context ctx,
                                       Response.Listener<JSONObject> callback,
                                       Response.ErrorListener errorCallback) {
        String url = String.format(Locale.getDefault(), "%s/create_student/%s", BASE_URL, STUDENT_ID);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null, callback, errorCallback);
        getInstance(ctx).getRequestQueue().add(req);
    }

    public static void deleteUser(Context ctx, String username,
                                  Response.Listener<JSONObject> callback,
                                  Response.ErrorListener errorCallback) {
        String url = String.format(Locale.getDefault(), "%s/delete_user/%s/%s", BASE_URL, STUDENT_ID, username);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, url, null, callback, errorCallback);
        getInstance(ctx).getRequestQueue().add(req);
    }
}
