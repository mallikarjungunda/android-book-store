package com.bookstore.andriod.bookstore.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InventoryDbHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "bookstore.db";
    public final static int DB_VERSION = 1;
    public final static String LOG_TAG = InventoryDbHelper.class.getCanonicalName();

    public InventoryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(StockContract.BookStoreConstants.CREATE_TABLE_BOOKSTORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertItem(StockItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockContract.BookStoreConstants.COLUMN_NAME, item.getProductName());
        values.put(StockContract.BookStoreConstants.COLUMN_PRICE, item.getPrice());
        values.put(StockContract.BookStoreConstants.COLUMN_QUANTITY, item.getQuantity());
        values.put(StockContract.BookStoreConstants.COLUMN_SUPPLIER_NAME, item.getSupplierName());
        values.put(StockContract.BookStoreConstants.COLUMN_SUPPLIER_PHONE, item.getSupplierPhone());
        values.put(StockContract.BookStoreConstants.COLUMN_SUPPLIER_EMAIL, item.getSupplierEmail());
        values.put(StockContract.BookStoreConstants.COLUMN_IMAGE, item.getImage());
        long result = db.insert(StockContract.BookStoreConstants.TABLE_NAME, null, values);
        if (result != -1) {
            Log.d(LOG_TAG, "Data inserted successfully with row ID " + result);
        } else {
            Log.d(LOG_TAG, "Insert unsuccessful");
        }
    }

    public Cursor readStock() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                StockContract.BookStoreConstants._ID,
                StockContract.BookStoreConstants.COLUMN_NAME,
                StockContract.BookStoreConstants.COLUMN_PRICE,
                StockContract.BookStoreConstants.COLUMN_QUANTITY,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_NAME,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_PHONE,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_EMAIL,
                StockContract.BookStoreConstants.COLUMN_IMAGE
        };
        Cursor cursor = db.query(
                StockContract.BookStoreConstants.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public Cursor readItem(long itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                StockContract.BookStoreConstants._ID,
                StockContract.BookStoreConstants.COLUMN_NAME,
                StockContract.BookStoreConstants.COLUMN_PRICE,
                StockContract.BookStoreConstants.COLUMN_QUANTITY,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_NAME,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_PHONE,
                StockContract.BookStoreConstants.COLUMN_SUPPLIER_EMAIL,
                StockContract.BookStoreConstants.COLUMN_IMAGE
        };
        String selection = StockContract.BookStoreConstants._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };

        Cursor cursor = db.query(
                StockContract.BookStoreConstants.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }

    public void updateItem(long currentItemId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockContract.BookStoreConstants.COLUMN_QUANTITY, quantity);
        String selection = StockContract.BookStoreConstants._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(currentItemId) };
        db.update(StockContract.BookStoreConstants.TABLE_NAME,
                values, selection, selectionArgs);
    }

    public void sellOneItem(long itemId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        int newQuantity = 0;
        if (quantity > 0) {
            newQuantity = quantity -1;
        }
        ContentValues values = new ContentValues();
        values.put(StockContract.BookStoreConstants.COLUMN_QUANTITY, newQuantity);
        String selection = StockContract.BookStoreConstants._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };
        db.update(StockContract.BookStoreConstants.TABLE_NAME,
                values, selection, selectionArgs);
    }
}
