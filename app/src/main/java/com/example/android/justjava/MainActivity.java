/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream=checkBox.isChecked();


        CheckBox chocoCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocoCheckBox.isChecked();


        EditText editText = (EditText) findViewById(R.id.name_customer);
        String nameCustomer = editText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage=createOrderSummary(nameCustomer,hasWhippedCream,hasChocolate,price);

        //disabled to not to show order summary on the screen
        //displayMessage(priceMessage);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Coffe Order For " + nameCustomer);
        i.putExtra(Intent.EXTRA_TEXT   , priceMessage);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

    }


    /**
     * Calculates the price of the order.
     *
     * Return the total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        int PricePercup=5;
        int WhippedCream=1;
        int Chocolate=2;

        if (hasWhippedCream) {
            PricePercup=PricePercup+WhippedCream;
        } else {
            //DO NOTHING
        }

            if (hasChocolate) {
                PricePercup=PricePercup+Chocolate;
        }   else {
                //DO NOTHING
        }

        return quantity*PricePercup;
    }

    /**
     * Creates the Summary of the Order
     * Return Price Messag to display
     */

    private String createOrderSummary(String nameCustomer,boolean WhippedCream,boolean hasChocolate,int price) {
        String priceMessage=getString(R.string.order_summary_name, nameCustomer);
        priceMessage = priceMessage + "\nWhipped Cream " + WhippedCream;
        priceMessage = priceMessage + "\nChocolate " + hasChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $"+price;
        priceMessage = priceMessage + "\n"+getString(R.string.thankYou);
        priceMessage = priceMessage + "\nPlease proceed to the counter.";
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.Quantity_value);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }



}