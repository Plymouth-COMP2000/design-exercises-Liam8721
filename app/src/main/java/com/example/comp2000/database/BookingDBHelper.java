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
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BOOKINGS = "bookings";

    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_PARTY_SIZE = "party_size";
    private static final String COL_GUEST_NAME = "guest_name";
    private static final String COL_NOTES = "notes";
    private static final String COL_IS_CONFIRMED = "is_confirmed";


    public BookingDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_DATE + " TEXT NOT NULL,"
                + COL_TIME + " TEXT NOT NULL,"
                + COL_PARTY_SIZE + " INTEGER NOT NULL,"
                + COL_GUEST_NAME + " TEXT NOT NULL,"
                + COL_NOTES + " TEXT,"
                + COL_IS_CONFIRMED + " INTEGER NOT NULL DEFAULT 0)"; // 0 for false, 1 for true
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
            onCreate(db);
        }
    }

    public boolean addBooking(Booking booking) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_DATE, booking.getDate());
        values.put(COL_TIME, booking.getTime());
        values.put(COL_PARTY_SIZE, booking.getPartySize());
        values.put(COL_GUEST_NAME, booking.getGuestName());
        values.put(COL_NOTES, booking.getNotes());
        values.put(COL_IS_CONFIRMED, booking.isConfirmed() ? 1 : 0);

        long result = db.insert(TABLE_BOOKINGS, null, values);
        return result != -1;
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PARTY_SIZE));
                String guestName = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_NAME));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
                boolean isConfirmed = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_CONFIRMED)) == 1;

                Booking booking = new Booking(date, time, partySize, guestName, notes);
                booking.setId(id);
                booking.setConfirmed(isConfirmed);

                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookingList;
    }

    public List<Booking> getBookingsByGuestName(String guestName) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_GUEST_NAME + " = ?";
        String[] selectionArgs = { guestName };

        Cursor cursor = db.query(
                TABLE_BOOKINGS,
                null, // all columns
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PARTY_SIZE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
                boolean isConfirmed = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IS_CONFIRMED)) == 1;

                Booking booking = new Booking(date, time, partySize, guestName, notes);
                booking.setId(id);
                booking.setConfirmed(isConfirmed);

                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookingList;
    }


    public int updateBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, booking.getDate());
        values.put(COL_TIME, booking.getTime());
        values.put(COL_PARTY_SIZE, booking.getPartySize());
        values.put(COL_GUEST_NAME, booking.getGuestName());
        values.put(COL_NOTES, booking.getNotes());
        values.put(COL_IS_CONFIRMED, booking.isConfirmed() ? 1 : 0);

        return db.update(TABLE_BOOKINGS, values, COL_ID + " = ?",
                new String[]{String.valueOf(booking.getId())});
    }

    public void deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKINGS, COL_ID + " = ?",
                new String[]{String.valueOf(bookingId)});
        db.close();
    }
}
