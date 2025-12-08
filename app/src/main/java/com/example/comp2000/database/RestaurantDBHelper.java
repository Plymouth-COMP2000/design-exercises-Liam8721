package com.example.comp2000.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDBHelper extends SQLiteOpenHelper {
    // New Reservation
    public long NewReservation(int PartySize, String BookingDate, String AdditionalInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues ReservationValues = new ContentValues();
        ReservationValues.put(COL_PARTY_SIZE, PartySize);
        ReservationValues.put(COL_DATE, BookingDate);
        ReservationValues.put(COL_ADDITIONAL_INFO, AdditionalInfo);

        return db.insert(TABLE_RESERVATIONS, null, ReservationValues);
    }


    // Database name and version
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    // Data Retrieval
    public Cursor getAllReservations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_RESERVATIONS,               // Table name
                new String[]{COL_RES_ID, COL_PARTY_SIZE, COL_DATE, COL_ADDITIONAL_INFO},  // Columns to return
                null,                             // Selection (WHERE clause)
                null,                             // Selection args
                null,                             // Group by
                null,                             // Having
                COL_DATE + " ASC"                 // Order by (optional, e.g., ascending by date)
        );
    }


    // Table names
    public static final String TABLE_MENU_CATEGORIES = "MenuCategories";
    public static final String TABLE_MENU_ITEMS = "MenuItems";
    public static final String TABLE_RESERVATIONS = "Reservations";

    // Categories table columns
    public static final String COL_CAT_ID = "cat_id";
    public static final String COL_CATEGORY = "category";

    // Menu table columns
    public static final String COL_MENU_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PRICE = "price";
    public static final String COL_IMAGE = "image";
    public static final String COL_MENU_CAT_ID = "cat_id";

    // Reservations table columns
    public static final String COL_RES_ID = "id";
    public static final String COL_PARTY_SIZE = "party_size";
    public static final String COL_DATE = "date";
    public static final String COL_ADDITIONAL_INFO = "additional_info";

    public RestaurantDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Categories table
        String CREATE_CATEGORIES = "CREATE TABLE " + TABLE_MENU_CATEGORIES + "("
                + COL_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_CATEGORY + " TEXT NOT NULL)";
        db.execSQL(CREATE_CATEGORIES);

        // Create Menu table
        String CREATE_MENU = "CREATE TABLE " + TABLE_MENU_ITEMS + "("
                + COL_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL,"
                + COL_PRICE + " REAL NOT NULL,"
                + COL_IMAGE + " TEXT NOT NULL,"
                + COL_MENU_CAT_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + COL_MENU_CAT_ID + ") REFERENCES " + TABLE_MENU_CATEGORIES + "(" + COL_CAT_ID + "))";
        db.execSQL(CREATE_MENU);

        // Create Reservations table
        String CREATE_RESERVATIONS = "CREATE TABLE " + TABLE_RESERVATIONS + "("
                + COL_RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PARTY_SIZE + " INTEGER NOT NULL,"
                + COL_DATE + " TEXT NOT NULL,"
                + COL_ADDITIONAL_INFO + " TEXT)";
        db.execSQL(CREATE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
