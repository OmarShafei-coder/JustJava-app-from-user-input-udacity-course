package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Global variables
     */
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //Getting the name from the edit txt view
        EditText editText = findViewById(R.id.edit_Text);
        String personName = editText.getText().toString();
        //does the user want toppings ?
        CheckBox state1 = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        CheckBox state2 = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasWhippedCream = state1.isChecked();
        boolean hasChocolate = state2.isChecked();
        //calculating the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, personName, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, (getString(R.string.email_subject) + " " + personName));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the order summary
     */
    public String createOrderSummary(int price, String personName, boolean hasWhippedCream, boolean hasChocolate) {

        String priceMessage;
        String addWhippedCream;
        String addChocolate;

        if(hasWhippedCream == true)
        {
            addWhippedCream = getString(R.string.yes);
        }
        else
        {
            addWhippedCream = getString(R.string.no);
        }

        if(hasChocolate == true)
        {
            addChocolate = getString(R.string.yes);
        }
        else
        {
            addChocolate = getString(R.string.no);
        }

        priceMessage = getString(R.string.order_summary_name);
        priceMessage += " " + personName;
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream);
        priceMessage += " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate);
        priceMessage += " " + addChocolate;
        priceMessage += "\n" + getString(R.string.order_summary_quantity);
        priceMessage += " " + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_total);
        priceMessage += price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        int price = quantity * basePrice;
        if (quantity > 0) {
            //if the user wants whipped cream, add $1
            if (hasWhippedCream) {
                price++;
            }
            //if the user wants chocolate, add $1
            if (hasChocolate) {
                price++;
            }
        }

        return price;
    }

    /**
     * This method is called when increment button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method is called when decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
        } else {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}

