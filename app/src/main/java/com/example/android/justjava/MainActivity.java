package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot order more than 100 coffees because there aren't that many bathrooms", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot order less than 1 coffee because you would be thirsty", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing to do
            return;
    }
        quantity = quantity - 1;

        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Customer name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Do they want whipped cream?
        CheckBox checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
        boolean hasWhippedCream = checkbox1.isChecked();

        //Do they want chocolate?
        CheckBox checkbox2 = (CheckBox) findViewById(R.id.checkbox2);
        boolean hasChocolate = checkbox2.isChecked();

        //Calculate price
        int orderSummary = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummaryMessage = createOrderSummary(name, orderSummary, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order_for) + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            }
        }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is is the customer wants whipped cream added
     * @param hasChocolate is if the customer wants chocolate added
     * @return total price
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add $1 for whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Add $2 for chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        //Calculate the total order by multiplying by quantity
        return quantity * basePrice;
        }


            /**
             * Create summary of the order.
             *
             * @param name of customer
             * @param totalPrice of the order
             * @param hasWhippedCream is whether or not the user wants whipped cream or not
             * @param hasChocolate is whether or not the user wants whipped cream or not
             * @return text summary
             */

        private String createOrderSummary (String name,int totalPrice, boolean hasWhippedCream,
        boolean hasChocolate){
            String orderSummaryMessage = getString(R.string.order_summary_name) + name;
            orderSummaryMessage += "\n" + getString(R.string.add_whipped_cream) + hasWhippedCream;
            orderSummaryMessage += "\n" + getString(R.string.add_chocolate) + hasChocolate;
            orderSummaryMessage += "\n" + getString(R.string.quantity)+ quantity;
            orderSummaryMessage += "\n" + getString(R.string.total)+ totalPrice;
            orderSummaryMessage += "\n" + getString(R.string.thank_you);
            return orderSummaryMessage;
        }


        /**
         * This method displays the given quantity value on the screen.
         */
        private void displayQuantity ( int number){
            TextView displayQuantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            displayQuantityTextView.setText("" + number);
        }

}

