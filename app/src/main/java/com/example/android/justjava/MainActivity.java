package com.example.android.justjava;


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

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // name field
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // whipped cream checkbox
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // chocolate checkbox
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);



            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
            intent.putExtra(Intent.EXTRA_TEXT, price);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream adds $1 to price
     * @param hasChocolate    adds $2 to price
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        // base price of cup of coffee
        int basePrice = 5;

        // price if whipped cream is added
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        // price if chocolate is added
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        // multiplies adjusted basePrice by quantity
        return basePrice = basePrice * quantity;
    }

    /**
     * Creates summary of order including name, quantity, total price and thank you.
     *
     * @param calculatePrice  of the order
     * @param name            of customer
     * @param addWhippedCream whether or not whipped cream is included
     * @param addChocolate    whether or not chocolate is included
     * @return summary of order
     */
    private String createOrderSummary(String name, int calculatePrice, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream?" + addWhippedCream;
        priceMessage += "\nAdd Chocolate?" + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + calculatePrice;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;

        // to prevent passing quantity over 100
        if (quantity > 100) {
            quantity = 100;
        }

        // prevents ordering more than 100 coffees
        if (quantity > 100) {

            // popup warning order maximum is 100
            Toast toast = Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;

        //to prevent passing negative quantity
        if (quantity < 1) {
            quantity = 0;
        }

        // to prevent negative quantity
        if (quantity < 1) {

            //popup warning must order at least 1
            Toast toast = Toast.makeText(this, "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT);
            toast.show();
            return;
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