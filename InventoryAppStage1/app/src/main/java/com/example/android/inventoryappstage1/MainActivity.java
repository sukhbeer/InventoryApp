package com.example.android.inventoryappstage1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventoryappstage1.data.StockContract;
import com.example.android.inventoryappstage1.data.StockContract.ItemEntry;
import com.example.android.inventoryappstage1.data.StockDbHelper;

public class MainActivity extends AppCompatActivity {

    private StockDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        dbHelper = new StockDbHelper(this);
        displayDatabaseInfo();
    }

    private Cursor queryData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_PRODUCT_NAME,
                ItemEntry.COLUMN_PRODUCT_PRICE,
                ItemEntry.COLUMN_PRODUCT_QUANTITY,
                ItemEntry.COLUMN_SUPPLIER_NAME,
                ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER,
        };


        Cursor cursor;
        cursor = db.query(ItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private void displayDatabaseInfo() {

        Cursor cursor = queryData();

        TextView displayView = findViewById(R.id.text_view_items);
        displayView.setText(getString(R.string.the_items_table_contains));
        displayView.append(" " + cursor.getCount() + " ");
        displayView.append(getString(R.string.items) + "\n\n");

        int idColumnIndex = cursor.getColumnIndex(StockContract.ItemEntry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_PRODUCT_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_SUPPLIER_NAME);
        int supplierContactColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        try {
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentProductPrice = cursor.getInt(productPriceColumnIndex);
                int currentProductQuantity = cursor.getInt(productQuantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierContact = cursor.getString(supplierContactColumnIndex);

                displayView.append("\n" + ItemEntry._ID + "  : " + currentID + "\n" +
                        ItemEntry.COLUMN_PRODUCT_NAME + "  : " + currentProductName + "\n" +
                        ItemEntry.COLUMN_PRODUCT_PRICE + "  : " + currentProductPrice + "\n" +
                        ItemEntry.COLUMN_PRODUCT_QUANTITY + "  : " + currentProductQuantity + "\n" +
                        ItemEntry.COLUMN_SUPPLIER_NAME + "  : " + currentSupplierName + "\n" +
                        ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "  : " + currentSupplierContact + "\n");
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
