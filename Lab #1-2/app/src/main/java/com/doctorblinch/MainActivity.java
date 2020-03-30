package com.doctorblinch;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements ButtonFragment.OnButtonFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinners();
    }


    private void initSpinners(){
        // Color menu
        final Spinner flowersColors = (Spinner) findViewById(R.id.flowerSpinner);

        ArrayAdapter<String> colorsAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.flowers_colors));

        colorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flowersColors.setAdapter(colorsAdapter);

        // Price menu

        final Spinner flowersPrice = (Spinner) findViewById(R.id.priceSpinner);

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

            LastOrder lastOrder = (LastOrder) getFragmentManager().findFragmentById(R.id.fragmentL);
            lastOrder.setLastOrderText(msg);

        }
    }

//    public void orderButtonClick(View view){
//        // Color menu
//        final Spinner flowersColors = (Spinner) findViewById(R.id.flowerSpinner);
//
//        // Price menu
//
//        final Spinner flowersPrice = (Spinner) findViewById(R.id.priceSpinner);
//
//        // Init EditText
//
//        final EditText editableText = (EditText) findViewById(R.id.flowerName);
//
//        // Button action
//
//        Context context = getApplicationContext();
//
//        CharSequence flowerText = editableText.getText();
//        String orderText;
//
//        if (flowerText.toString().equals("")){
//            orderText = "Please fill all columns";
//        }
//        else {
//            orderText = String.format(
//                    "You ordered %s. The color is %s and the price range is %s",
//                    flowerText,
//                    flowersColors.getSelectedItem().toString(),
//                    flowersPrice.getSelectedItem().toString()
//            );
//        }
//        Toast toast = Toast.makeText(context, orderText, Toast.LENGTH_LONG);
//        toast.show();
//
//    }

}
