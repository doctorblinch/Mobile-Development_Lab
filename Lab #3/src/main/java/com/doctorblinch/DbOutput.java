package com.doctorblinch;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DbOutput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_output);

        show();
    }

    private void show(){
        ArrayList<String> allRecords = getIntent().getStringArrayListExtra("allRecords");

        final TextView outputTextView = findViewById(R.id.output);

        StringBuilder result = new StringBuilder();
        assert allRecords != null;

        if (allRecords.size() > 0) {
            for (String record: allRecords) {
                result.append(record);
            }
        } else {
            String empty_table_msg = getResources().getString(R.string.empty_table_msg);
            result.append(empty_table_msg);
        }



        outputTextView.setText(result);

    }
}
