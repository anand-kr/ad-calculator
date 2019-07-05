package com.example.calculator_a;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};

    private int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};

    private TextView txtScreen,txtScreen1;

    private boolean lastNumeric;

    private boolean stateError;

    private boolean lastDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        this.txtScreen = (TextView) findViewById(R.id.txtScreen);


        setNumericOnClickListener();

        setOperatorOnClickListener();
    }


    private void setNumericOnClickListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button button = (Button) v;
                if (stateError) {

                    txtScreen.setText(button.getText());
                    stateError = false;
                } else {

                    txtScreen.append(button.getText());
                }

                lastNumeric = true;
            }
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }


    private void setOperatorOnClickListener() {

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtScreen.setText("");  // Clear the screen
                // Reset all the states and flags
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });
        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }


    private void onEqual() {

        if (lastNumeric && !stateError) {

            String txt = txtScreen.getText().toString();

            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                double result = expression.evaluate();
                txtScreen.setText(Double.toString(result));
                lastDot = true; // Result contains a dot
            } catch (ArithmeticException ex) {
                // Display an error message
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
        else{

        }
    }
}