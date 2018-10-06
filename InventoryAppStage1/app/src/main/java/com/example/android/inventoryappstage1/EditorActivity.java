package com.example.android.inventoryappstage1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryappstage1.data.StockContract.ItemEntry;
import com.example.android.inventoryappstage1.data.StockDbHelper;

public class EditorActivity extends AppCompatActivity {

    public StockDbHelper dbHelper;
    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productQuantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierContactEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        productNameEditText = findViewById(R.id.product_name);
        productPriceEditText = findViewById(R.id.poduct_price);
        productQuantityEditText = findViewById(R.id.product_quantity);
        supplierNameEditText = findViewById(R.id.supplier_name);
        supplierContactEditText = findViewById(R.id.supplier_contact);
        dbHelper = new StockDbHelper(this);
    }

    private void insertBook() {
        String productName;
        if (TextUtils.isEmpty(productNameEditText.getText())) {
            productNameEditText.setError(getString(R.string.required_field));
            return;
        } else {
            productName = productNameEditText.getText().toString().trim();
        }

        String productPrice;
        if (TextUtils.isEmpty(productPriceEditText.getText())) {
            productPriceEditText.setError(getString(R.string.required_field));
            return;
        } else {
            productPrice = productPriceEditText.getText().toString().trim();
        }

        String productQuantity;
        if (TextUtils.isEmpty(productQuantityEditText.getText())) {
            productQuantityEditText.setError(getString(R.string.required_field));
            return;
        } else {
            productQuantity = productQuantityEditText.getText().toString().trim();
        }

        String supplierName;
        if (TextUtils.isEmpty(supplierNameEditText.getText())) {
            supplierNameEditText.setError(getString(R.string.required_field));
            return;
        } else {
            supplierName = supplierNameEditText.getText().toString().trim();
        }

        String supplierContact;
        if (TextUtils.isEmpty(supplierContactEditText.getText())) {
            supplierContactEditText.setError(getString(R.string.required_field));
            return;
        } else {
            supplierContact = supplierContactEditText.getText().toString().trim();
        }

        int productPriceInt = Integer.parseInt(productPrice);
        if(productPriceInt < 0){
            productPriceEditText.setError(getString(R.string.price_cannot_be_negative));
            Toast.makeText(this, getString(R.string.price_cannot_be_negative), Toast.LENGTH_SHORT).show();
            return;
        }
        int productQuantityInt = Integer.parseInt(productQuantity);
        if(productQuantityInt < 0){
            productQuantityEditText.setError(getString(R.string.quantity_cannot_be_negative));
            Toast.makeText(this, getString(R.string.quantity_cannot_be_negative), Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(ItemEntry.COLUMN_PRODUCT_PRICE, productPriceInt);
        values.put(ItemEntry.COLUMN_PRODUCT_QUANTITY, productQuantityInt);
        values.put(ItemEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierContact);

        long newRowId = db.insert(ItemEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, getString(R.string.error_saving_book), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.items_saved) + " " + newRowId, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBook();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
