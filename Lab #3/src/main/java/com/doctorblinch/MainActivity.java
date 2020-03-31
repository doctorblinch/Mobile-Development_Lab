package com.doctorblinch;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ButtonFragment.OnButtonFragmentInteractionListener{

    private Spinner flowersColors, flowersPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinners();
    }


    private void initSpinners(){
        // Color menu
        flowersColors = (Spinner) findViewById(R.id.flowerSpinner);

        ArrayAdapter<String> colorsAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.flowers_colors));

        colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flowersColors.setAdapter(colorsAdapter);

        // Price menu

        flowersPrice = (Spinner) findViewById(R.id.priceSpinner);

        ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.flowers_prices));

        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flowersPrice.setAdapter(priceAdapter);
    }

    @Override
    public void onOrderClick() {
        // Color menu
        final Spinner flowersColors = (Spinner) findViewById(R.id.flowerSpinner);

        // Price menu

        final Spinner flowersPrice = (Spinner) findViewById(R.id.priceSpinner);

        // Init EditText

        final EditText editableText = (EditText) findViewById(R.id.flowerName);

        // Button action

        show_order(
                flowersColors.getSelectedItem().toString(),
                flowersPrice.getSelectedItem().toString(),
                editableText.getText().toString()
        );

    }

    private void show_order(String color, String price, String text) {
        if (text.equals("")) {
            String msg = "Please fill all columns";
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String msg = String.format(
                    "You ordered %s. The color is %s and the price range is %s.",
                    text, color, price);

            //Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            //toast.show();
            LastOrder lastOrder = (LastOrder) getFragmentManager().findFragmentById(R.id.fragmentL);
            lastOrder.setLastOrderText(msg);

        }
    }

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("colorSpinnerPosition", flowersColors.getSelectedItemPosition());
        savedInstanceState.putInt("priceSpinnerPosition", flowersPrice.getSelectedItemPosition());
    }

    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        flowersColors.setSelection(savedInstanceState.getInt("colorSpinnerPosition"));
        flowersPrice.setSelection(savedInstanceState.getInt("priceSpinnerPosition"));
    }

    // Робота з базою даних
    private void insertRecord(String color, String price) {
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String records_table_name = getResources().getString(R.string.records_table_name);
        String create_table = getResources().getString(R.string.create_table);
        String color_column_name = getResources().getString(R.string.color_column_name);
        String price_column_name = getResources().getString(R.string.price_column_name);

        ContentValues cv = new ContentValues();
        cv.put(color_column_name, color);
        cv.put(price_column_name, price);

        String query = String.format(create_table, records_table_name, color_column_name, price_column_name);
        db.execSQL(query);
        db.insert(records_table_name, null, cv);

        db.close();
        Toast toast = Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onClickInsertRecord(View view) {
        final Spinner flowersColors = (Spinner) findViewById(R.id.flowerSpinner);

        final Spinner flowersPrice = (Spinner) findViewById(R.id.priceSpinner);

        String color = flowersColors.getSelectedItem().toString();
        String price = flowersPrice.getSelectedItem().toString();

        insertRecord(color, price);
    };


    private List<String> getAllRecords() {
        List<String> allRecords = new ArrayList<>();
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String select_all_from_table = getResources().getString(R.string.select_all_from_table);
        String records_table_name = getResources().getString(R.string.records_table_name);

        Cursor query = db.rawQuery(String.format(select_all_from_table, records_table_name), null);
        if(query.moveToFirst()){
            do {
                int id = query.getInt(0);
                String color = query.getString(1);
                String price = query.getString(2);

                allRecords.add(id + ". Color: " + color + "; Price: " + price + "\n");
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return allRecords;
    }

    public void showRecords(View view) {
        Intent intent = new Intent(this, DbOutput.class);

        ArrayList<String> als = (ArrayList<String>) getAllRecords();

        intent.putStringArrayListExtra("allRecords", (ArrayList<String>) als);

        startActivity(intent);
    }

    public void clearTable(View view) {
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String records_table_name = getResources().getString(R.string.records_table_name);
        String delete_from_table = getResources().getString(R.string.delete_from_table);

        db.execSQL(String.format(delete_from_table, records_table_name));

        db.close();
        Toast toast = Toast.makeText(this, "Cleared successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

}
