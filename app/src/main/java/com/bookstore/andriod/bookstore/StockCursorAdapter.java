package com.bookstore.andriod.bookstore;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookstore.andriod.bookstore.data.StockContract;

public class StockCursorAdapter extends CursorAdapter {


    private final BookStoreActivity activity;

    public StockCursorAdapter(BookStoreActivity context, Cursor c) {
        super(context, c, 0);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.product_name);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageView sale = view.findViewById(R.id.sale);
        ImageView image = view.findViewById(R.id.image_view);

        String name = cursor.getString(cursor.getColumnIndex(StockContract.BookStoreConstants.COLUMN_NAME));
        final int quantity = cursor.getInt(cursor.getColumnIndex(StockContract.BookStoreConstants.COLUMN_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndex(StockContract.BookStoreConstants.COLUMN_PRICE));

        image.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(StockContract.BookStoreConstants.COLUMN_IMAGE))));

        nameTextView.setText(name);
        quantityTextView.setText(String.valueOf(quantity));
        priceTextView.setText(price);

        final long id = cursor.getLong(cursor.getColumnIndex(StockContract.BookStoreConstants._ID));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.clickOnViewItem(id);
            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.clickOnSale(id,
                        quantity);
            }
        });
    }
}
