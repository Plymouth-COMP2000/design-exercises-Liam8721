package com.example.comp2000.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookingDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bookings.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_BOOKINGS = "bookings";

    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_PARTY_SIZE = "party_size";
    private static final String COL_GUEST_NAME = "guest_name";
    private static final String COL_GUEST_USERNAME = "guest_username";
    private static final String COL_GUEST_EMAIL = "guest_email";
    private static final String COL_NOTES = "notes";

    public BookingDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " TEXT NOT NULL, "
                + COL_TIME + " TEXT NOT NULL, "
                + COL_PARTY_SIZE + " INTEGER NOT NULL, "
                + COL_GUEST_NAME + " TEXT NOT NULL, "
                + COL_GUEST_USERNAME + " TEXT NOT NULL, "
                + COL_GUEST_EMAIL + " TEXT NOT NULL, "
                + COL_NOTES + " TEXT"
                + ")";
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    public boolean addBooking(Booking booking) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_DATE, booking.getDate());
        values.put(COL_TIME, booking.getTime());
        values.put(COL_PARTY_SIZE, booking.getPartySize());
        values.put(COL_GUEST_NAME, booking.getGuestName());
        values.put(COL_GUEST_USERNAME, booking.getGuestUsername());
        values.put(COL_GUEST_EMAIL, booking.getGuestEmail());
        values.put(COL_NOTES, booking.getNotes());

        long result = db.insert(TABLE_BOOKINGS, null, values);
        return result != -1;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKINGS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = bookingFromCursor(cursor);
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookingList;
    }

    public List<Booking> getBookingsByUsername(String username) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_BOOKINGS,
                null,
                COL_GUEST_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Booking booking = bookingFromCursor(cursor);
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookingList;
    }

    public List<Booking> getBookingsByEmail(String email) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_BOOKINGS,
                null,
                COL_GUEST_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Booking booking = bookingFromCursor(cursor);
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookingList;
    }

    public int updateBooking(Booking booking) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_DATE, booking.getDate());
        values.put(COL_TIME, booking.getTime());
        values.put(COL_PARTY_SIZE, booking.getPartySize());
        values.put(COL_GUEST_NAME, booking.getGuestName());
        values.put(COL_GUEST_USERNAME, booking.getGuestUsername());
        values.put(COL_GUEST_EMAIL, booking.getGuestEmail());
        values.put(COL_NOTES, booking.getNotes());

        return db.update(
                TABLE_BOOKINGS,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(booking.getId())}
        );
    }

    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BOOKINGS, COL_ID + " = ?", new String[]{String.valueOf(bookingId)});
        db.close();
    }

    private Booking bookingFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
        int partySize = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PARTY_SIZE));
        String guestName = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_NAME));
        String guestUsername = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_USERNAME));
        String guestEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_EMAIL));
        String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));

        Booking booking = new Booking(date, time, partySize, guestName, guestUsername, guestEmail, notes);
        booking.setId(id);
        return booking;
    }
}
