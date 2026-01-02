package com.example.comp2000.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.comp2000.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Menu.db";
    private static final int DATABASE_VERSION = 2; // Incremented version to trigger onUpgrade

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_MENU_ITEMS = "menu_items";

    private static final String CAT_COL_ID = "id";
    private static final String CAT_COL_NAME = "name";

    private static final String ITEM_COL_ID = "id";
    private static final String ITEM_COL_NAME = "name";
    private static final String ITEM_COL_DESC = "description";
    private static final String ITEM_COL_PRICE = "price";
    private static final String ITEM_COL_IMAGE_BLOB = "image_blob";
    private static final String ITEM_COL_CAT_ID_FK = "category_id";

    public MenuDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + CAT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CAT_COL_NAME + " TEXT NOT NULL UNIQUE)";

        String CREATE_MENU_ITEMS_TABLE = "CREATE TABLE " + TABLE_MENU_ITEMS + "("
                + ITEM_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ITEM_COL_NAME + " TEXT NOT NULL,"
                + ITEM_COL_DESC + " TEXT,"
                + ITEM_COL_PRICE + " REAL NOT NULL,"
                + ITEM_COL_IMAGE_BLOB + " BLOB,"
                + ITEM_COL_CAT_ID_FK + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + ITEM_COL_CAT_ID_FK + ") REFERENCES " + TABLE_CATEGORIES + "(" + CAT_COL_ID + "))";

        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_MENU_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
            onCreate(db);
        }
    }

    // --- Category Methods ---
    public boolean addCategory(Category category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAT_COL_NAME, category.getName());
        long result = db.insert(TABLE_CATEGORIES, null, values);
        return result != -1;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CAT_COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CAT_COL_NAME));
                Category category = new Category(name);
                category.setId(id);
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

    public boolean addMenuItem(MenuItem menuItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_COL_NAME, menuItem.getName());
        values.put(ITEM_COL_DESC, menuItem.getDescription());
        values.put(ITEM_COL_PRICE, menuItem.getPrice());
        values.put(ITEM_COL_IMAGE_BLOB, menuItem.getImageBlob());
        values.put(ITEM_COL_CAT_ID_FK, menuItem.getCategoryId());

        long result = db.insert(TABLE_MENU_ITEMS, null, values);
        return result != -1;
    }

    public List<MenuItem> getMenuItemsByCategory(int categoryId) {
        List<MenuItem> itemList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU_ITEMS,
                new String[]{ITEM_COL_ID, ITEM_COL_NAME, ITEM_COL_DESC, ITEM_COL_PRICE, ITEM_COL_IMAGE_BLOB, ITEM_COL_CAT_ID_FK},
                ITEM_COL_CAT_ID_FK + " = ?",
                new String[]{String.valueOf(categoryId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ITEM_COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_COL_NAME));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_COL_DESC));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow(ITEM_COL_PRICE));
                byte[] imageBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(ITEM_COL_IMAGE_BLOB));

                MenuItem item = new MenuItem(name, desc, price, imageBlob, categoryId);
                item.setId(id);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public int updateMenuItem(MenuItem menuItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_COL_NAME, menuItem.getName());
        values.put(ITEM_COL_DESC, menuItem.getDescription());
        values.put(ITEM_COL_PRICE, menuItem.getPrice());
        values.put(ITEM_COL_IMAGE_BLOB, menuItem.getImageBlob());
        values.put(ITEM_COL_CAT_ID_FK, menuItem.getCategoryId());

        return db.update(TABLE_MENU_ITEMS, values, ITEM_COL_ID + " = ?",
                new String[]{String.valueOf(menuItem.getId())});
    }

    public void deleteMenuItem(int menuItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU_ITEMS, ITEM_COL_ID + " = ?", new String[]{String.valueOf(menuItemId)});
        db.close();
    }
}
