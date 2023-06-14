package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare UI elements
    MaterialButton allclear, clear, backspace;
    MaterialButton add, subtract, multiply, divide, modulo;
    MaterialButton equalto, dot;
    MaterialButton zero, one, two, three, four, five, six, seven, eight, nine;
    TextView operation, result;

    // Declare variables
    String input = "", output;
    ArrayList<String> operands = new ArrayList<String>();
    double finalResult = (0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        defineHooks();

        // Set click listeners
        setListener();

        // Hide operation TextView and set result to 0
        result.setText("0");

    }

    private void setListener() {
        // Set click listeners for all buttons
        allclear.setOnClickListener(this);
        clear.setOnClickListener(this);
        backspace.setOnClickListener(this);
        modulo.setOnClickListener(this);
        divide.setOnClickListener(this);
        multiply.setOnClickListener(this);
        subtract.setOnClickListener(this);
        add.setOnClickListener(this);
        equalto.setOnClickListener(this);
        dot.setOnClickListener(this);
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

    }

    private void defineHooks() {
        // Initialize UI elements
        operation = findViewById(R.id.data_tv);
        result = findViewById(R.id.result_tv);
        allclear = findViewById(R.id.btn_allclear);
        clear = findViewById(R.id.btn_clear);
        backspace = findViewById(R.id.btn_backspace);
        add = findViewById(R.id.btn_add);
        subtract = findViewById(R.id.btn_subtract);
        multiply = findViewById(R.id.btn_multiply);
        divide = findViewById(R.id.btn_divide);
        modulo = findViewById(R.id.btn_modulo);
        equalto = findViewById(R.id.btn_equalto);
        dot = findViewById(R.id.btn_dot);
        zero = findViewById(R.id.btn_0);
        one = findViewById(R.id.btn_1);
        two = findViewById(R.id.btn_2);
        three = findViewById(R.id.btn_3);
        four = findViewById(R.id.btn_4);
        five = findViewById(R.id.btn_5);
        six = findViewById(R.id.btn_6);
        seven = findViewById(R.id.btn_7);
        eight = findViewById(R.id.btn_8);
        nine = findViewById(R.id.btn_9);
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (v == zero) {
            operation("0");
        } else if (v == one) {
            operation("1");
        } else if (v == two) {
            operation("2");
        } else if (v == three) {
            operation("3");
        } else if (v == four) {
            operation("4");
        } else if (v == five) {
            operation("5");
        } else if (v == six) {
            operation("6");
        } else if (v == seven) {
            operation("7");
        } else if (v == eight) {
            operation("8");
        } else if (v == nine) {
            operation("9");
        } else if (v == dot) {
            operation(".");
        } else if (v == add) {
            operation("+");
        } else if (v == subtract) {
            operation("-");
        } else if (v == multiply) {
            operation("*");
        } else if (v == divide) {
            operation("/");
        } else if (v == modulo) {
            operation("%");
        } else if (v == backspace) {
            operation("<-");
        } else if (v == clear) {
            operation("C");
        } else if (v == allclear) {
            operation("AC");
        } else if (v == equalto) {
            operation("result");
        }

    }

    private void operation(String no) {
        // Perform the calculator operations
        if (no.equals("AC")) {
            // Clear all input and result
            input = "";
            result.setText("0");
        } else if (no.equals("C")) {
            // Clear only input
            input = "";
        } else if (no.equals("<-")) {
            // Remove the last character
            if (!input.equals("")) {
                input = input.substring(0, input.length() - 1);
            }
        } else if (no.equals("result")) {
            // Evaluate the input expression
            getResult();
        }else {
            // Check for valid input based on previous and current values
            boolean isTwiceOperator = false;
            String prev = "";
            if (input.length() > 1) {
                prev = input.substring(input.length() - 1);
            }
            if ((prev.equals("+") || prev.equals("*") || prev.equals("-") || prev.equals("/") || prev.equals("%") || prev.equals(".")) && (no.equals("+") || no.equals("-") || no.equals("%") || no.equals("/") || no.equals("*") || no.equals("."))) {
                isTwiceOperator = true;
            }
            if (input.equals("") && (no.equals("+") || no.equals("-") || no.equals("%") || no.equals("*") || no.equals("/") || no.equals("."))) {
                isTwiceOperator = true;
            }
            if (!isTwiceOperator) {
                input = input + no;
            }
        }
        operation.setText(input);
    }

    private void getResult() {
        // Evaluate the expression using JavaScript engine
        input = operation.getText().toString();

        // Replace '%' with '/100' for percentage calculations
        input=input.replaceAll("%","/100");

        Context rhino=Context.enter();
        rhino.setOptimizationLevel(-1);

        String finalResult="";

        Scriptable scriptable=rhino.initStandardObjects();
        finalResult=rhino.evaluateString(scriptable,input,"Javsscript",1,null).toString();
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","");
        }
        result.setText(finalResult);

    }
}
