package com.example.mike.candystoreramirez;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);

    }

    public void insert(View v) {
        EditText nameEditText = (EditText) findViewById(R.id.insert_name_edit_text);
        EditText priceEditText = (EditText) findViewById(R.id.insert_price_edit_text);
        String name = nameEditText.getText().toString();
        String priceString = priceEditText.getText().toString();

        try {
            double price = Double.parseDouble(priceString);
            Candy candy = new Candy(0, name, price);
            dbManager.insert(candy);
            Toast.makeText(this, "A Candy Added!", Toast.LENGTH_LONG).show();
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, "Price Error", Toast.LENGTH_LONG).show();
        }

        nameEditText.setText("");
        priceEditText.setText("");
    }

    public void goBack(View v) {
        this.finish();
    }

}
