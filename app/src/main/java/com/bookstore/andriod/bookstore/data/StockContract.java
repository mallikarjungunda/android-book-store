package com.bookstore.andriod.bookstore.data;

import android.provider.BaseColumns;

public class StockContract {

    public StockContract() {
    }

    public static final class BookStoreConstants implements BaseColumns {

        public static final String TABLE_NAME = "bookstore";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplier_email";
        public static final String COLUMN_IMAGE = "image";

        public static final String CREATE_TABLE_BOOKSTORE = "CREATE TABLE " +
                BookStoreConstants.TABLE_NAME + "(" +
                BookStoreConstants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BookStoreConstants.COLUMN_NAME + " TEXT NOT NULL," +
                BookStoreConstants.COLUMN_PRICE + " TEXT NOT NULL," +
                BookStoreConstants.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                BookStoreConstants.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                BookStoreConstants.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL," +
                BookStoreConstants.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL," +
                BookStoreConstants.COLUMN_IMAGE + " TEXT NOT NULL" + ");";
    }
}