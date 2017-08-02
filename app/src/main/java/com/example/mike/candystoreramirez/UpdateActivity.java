package com.example.mike.candystoreramirez;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {


    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DatabaseManager(this);
        updateView();
    }

    public void updateView() {
        ArrayList<Candy> candies = dbManager.selectAll();
        if (candies.size() > 0) {

            ScrollView scrollView = new ScrollView(this);

            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setRowCount(candies.size());
            gridLayout.setColumnCount(4);



            TextView[] ids = new TextView[candies.size()];
            EditText[][] namesAndPrices = new EditText[candies.size()][2];
            Button[] buttons = new Button[candies.size()];

            ButtonHandler buttonHandler = new ButtonHandler();

            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);

            int width = size.x;

            int i = 0;

            for (Candy candy : candies) {

                ids[i] = new TextView(this);
                ids[i].setGravity(Gravity.CENTER);
                ids[i].setText("" + candy.getId());

                namesAndPrices[i][0] = new EditText(this);
                namesAndPrices[i][1] = new EditText(this);
                namesAndPrices[i][0].setText(candy.getName());
                namesAndPrices[i][1].setText("" + candy.getPrice());
                namesAndPrices[i][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                namesAndPrices[i][0].setId(10 * candy.getId());
                namesAndPrices[i][1].setId(10 * candy.getId() + 1);

                buttons[i] = new Button(this);
                buttons[i].setText("Update");
                buttons[i].setId(candy.getId());

                buttons[i].setOnClickListener(buttonHandler);

                gridLayout.addView(ids[i], width / 10, ViewGroup.LayoutParams.WRAP_CONTENT);

                gridLayout.addView(namesAndPrices[i][0], (int) (width * .4),
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                gridLayout.addView(namesAndPrices[i][1], (int) (width * .15),
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                gridLayout.addView(buttons[i], (int) (width * .35),
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                i++;

            }


            scrollView.addView(gridLayout);
            setContentView(scrollView);

        }

    }


    private class ButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int candyId = v.getId();
            EditText nameET = (EditText) findViewById(10 * candyId);
            EditText priceET = (EditText) findViewById(10 * candyId + 1);
            String name = nameET.getText().toString();
            String priceString = priceET.getText().toString();

            try {
                double price = Double.parseDouble(priceString);
                dbManager.updateByID(candyId, name, price);
                Toast.makeText(UpdateActivity.this, "Candy Updated!", Toast.LENGTH_SHORT).show();
                updateView();
            } catch (NumberFormatException nfe) {
                Toast.makeText(UpdateActivity.this, "Price Error!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
