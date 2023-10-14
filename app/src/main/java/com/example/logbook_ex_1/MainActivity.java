package com.example.logbook_ex_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText resultEditText;
    private String currentInput = "";
    private double currentResult = 0;
    private String operator = "";

    private Boolean removeLastCharacter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEditText = findViewById(R.id.resultEditText);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if(buttonText.matches("[0-9]")) {
            onNumberClick(view);
        } else if (buttonText.matches("[+\\-x*÷]")) {
            onOperatorClick(view);
        } else if (buttonText.equals("=")) {
            onEqualsClick();
        } else if (buttonText.equals("DEL")) {
            removeLastCharacter();
        } else if (buttonText.equals("AC")) {
            onClearClick();
        } else if (buttonText.equals(".")) {
            onDecimalClick();
        }
    }

    public void removeLastCharacter() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
        removeLastCharacter = true;
        updateResultText();
    }

    public void onDecimalClick() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            updateResultText();
        }
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        updateResultText();
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        if(!operator.isEmpty()){
            onEqualsClick();
        }
        if (!currentInput.isEmpty()) {
            currentResult = Double.parseDouble(currentInput);
            currentInput = "";
        }
        operator = button.getText().toString();
        updateResultText();
    }

    public void onEqualsClick() {
        if (!currentInput.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            switch (operator) {
                case "+":
                    currentResult += secondOperand;
                    break;
                case "-":
                    currentResult -= secondOperand;
                    break;
                case "*":
                    currentResult *= secondOperand;
                    break;
                case "÷":
                    if (secondOperand != 0) {
                        currentResult /= secondOperand;
                    } else {
                        currentInput = "";
                        operator = "";
                        currentResult = 0;
                        resultEditText.setText("Error");
                        return;
                    }
                    break;
            }
            String behindDot = String.valueOf(currentResult).substring(String.valueOf(currentResult).indexOf("."));
            if(behindDot.equals(".0")){
                Long currentResultConverted = Math.round(currentResult);
                currentInput = String.valueOf(currentResultConverted);
            }
            else{
                currentInput = String.valueOf(currentResult);
            }
            operator = "";
            updateResultText();
        }
    }

    public void onClearClick() {
        currentInput = "";
        operator = "";
        currentResult = 0;
        updateResultText();
    }

    private void updateResultText() {
        if(!operator.isEmpty()) {
            String lastText = resultEditText.getText().toString();
            if (lastText.length() == 0) {
                lastText = currentInput;
                currentInput = "";
                currentResult = 0;
            }
            else if(lastText.equals("Error")) {
                lastText = "";
            }
            else if (removeLastCharacter) {
                lastText = lastText.substring(0, lastText.length() - 1);
                removeLastCharacter = false;
            }
            else if(!lastText.substring(lastText.length() - 1).matches("[+\\-x*÷]") && !currentInput.contains(".")) {
                lastText += operator;
            }
            else if (currentInput.contains(".")){
                lastText = lastText.substring(0, lastText.indexOf(operator) + 1);
                lastText += currentInput;
            }else{
                lastText += currentInput;
            }
            resultEditText.setText(lastText);
        }
        else{
            resultEditText.setText(currentInput);
        }
    }
}
