package com.bookstore.andriod.bookstore;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bookstore.andriod.bookstore.data.InventoryDbHelper;
import com.bookstore.andriod.bookstore.data.StockItem;
import android.support.design.widget.FloatingActionButton;

public class BookStoreActivity extends AppCompatActivity {

    private final static String LOG_TAG = BookStoreActivity.class.getCanonicalName();
    InventoryDbHelper dbHelper;
    StockCursorAdapter adapter;
    int lastVisibleItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store);
        dbHelper = new InventoryDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookStoreActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new StockCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                if (currentFirstVisibleItem > lastVisibleItem) {
                    fab.show();
                } else if (currentFirstVisibleItem < lastVisibleItem) {
                    fab.hide();
                }
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Add data for demo purposes
     */
    private void addDummyData() {
        StockItem gummibears = new StockItem(
                "Gummibears",
                "$20",
                45,
                "Haribo GmbH",
                "+1 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.bookstore.andriod.bookstore/drawable/gummibear");
        dbHelper.insertItem(gummibears);

        StockItem peaches = new StockItem(
                "Peaches",
                "$30",
                24,
                "Haribo GmbH",
                "+1 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.bookstore.andriod.bookstore/drawable/gummibear");
        dbHelper.insertItem(peaches);

        StockItem cherries = new StockItem(
                "Cherries",
                "$11",
                74,
                "Haribo GmbH",
                "+1 000 000 0000",
                "haribo@sweet.com",
                "android.resource://com.bookstore.andriod.bookstore/drawable/gummibear");
        dbHelper.insertItem(cherries);
    }
}
