package edu.homework.calculator;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import kotlin.collections.DoubleIterator;

public class MainActivity extends AppCompatActivity {

    TextView textOperation;
    TextView textInput;
    Double firstOperand = null;
    Double secondOperand = null;

    String inputOperand = "";

    Operation currentOperation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textOperation = findViewById(R.id.textOperation);
        textInput = findViewById(R.id.textInput);

        findViewById(R.id.buttonPercent).setEnabled(false);

        findViewById(R.id.buttonPlus).setOnClickListener((view)->onOperationClick(Operation.addition));
        findViewById(R.id.buttonMinus).setOnClickListener((view)->onOperationClick(Operation.subtraction));
        findViewById(R.id.buttonMultiplication).setOnClickListener((view)->onOperationClick(Operation.multiplication));
        findViewById(R.id.buttonDivision).setOnClickListener((view)->onOperationClick(Operation.division));

        findViewById(R.id.buttonEquals).setOnClickListener((view)->calc());

        findViewById(R.id.buttonClear).setOnClickListener((view)-> сlear());
        findViewById(R.id.buttonBackspace).setOnClickListener((view)->onBackspaceClick());

        findViewById(R.id.button0).setOnClickListener((view)->onNumberClick("0"));
        findViewById(R.id.button1).setOnClickListener((view)->onNumberClick("1"));
        findViewById(R.id.button2).setOnClickListener((view)->onNumberClick("2"));
        findViewById(R.id.button3).setOnClickListener((view)->onNumberClick("3"));
        findViewById(R.id.button4).setOnClickListener((view)->onNumberClick("4"));
        findViewById(R.id.button5).setOnClickListener((view)->onNumberClick("5"));
        findViewById(R.id.button6).setOnClickListener((view)->onNumberClick("6"));
        findViewById(R.id.button7).setOnClickListener((view)->onNumberClick("7"));
        findViewById(R.id.button8).setOnClickListener((view)->onNumberClick("8"));
        findViewById(R.id.button9).setOnClickListener((view)->onNumberClick("9"));

        findViewById(R.id.buttonPoint).setOnClickListener((view)->onPointClick());
        findViewById(R.id.buttonPlusMinus).setOnClickListener((view)->changeSign());
    }

    private void сlear() {
        setInputOperand("");
        currentOperation = null;
        textOperation.setText("");
        firstOperand = null;
        secondOperand = null;
    }

    private void onBackspaceClick() {
        if(inputOperand != null && !inputOperand.isEmpty()) {
            setInputOperand(inputOperand.substring(0, inputOperand.length() - 1));
        }
        else if(currentOperation != null)
        {
            unsetCurrentOperation();
            if(firstOperand != null)
                setInputOperand(DouleToString(firstOperand));
            firstOperand = null;
            textOperation.setText("");
        }
    }

    private void setInputOperand(String operand) {
        inputOperand = operand;
        textInput.setText(operand);
    }

    private void setFirstOperand(String operand) {
        firstOperand = getNumberFromString(operand);
        textOperation.setText(DouleToString(firstOperand));
    }

    private Double getNumberFromString(String operand) {
        if(operand == null || operand.isEmpty())
            return 0.0;
        else
            return Double.parseDouble(operand.replace(",","."));
    }

    private void unsetCurrentOperation() {
        currentOperation = null;
    }

    private void setCurrentOperation(Operation operation) {
        if(firstOperand == null) {
            setFirstOperand(inputOperand);
            setInputOperand("");
        }
        unsetCurrentOperation();
        currentOperation = operation;
        textOperation.setText(DouleToString(firstOperand) + operation.toString());
    }

    private void onOperationClick(Operation operation) {
        setCurrentOperation(operation);
    }

    private void onNumberClick(String number) {
        inputOperand += number;
        textInput.setText(inputOperand);
    }

    private void onPointClick() {
        if(inputOperand.contains("."))
            return;
        String temp = inputOperand;
        if(inputOperand.isEmpty()){
            temp = "0";
        }
        temp += ".";
        setInputOperand(temp);
    }

    private void changeSign() {
        if(inputOperand == null || inputOperand.isEmpty())
            return;
        Double temp = getNumberFromString(inputOperand);
        temp *= -1;
        setInputOperand(DouleToString(temp));
    }

    private void calc() {
        if(firstOperand == null || currentOperation == null || inputOperand.isEmpty())
            return;
        secondOperand = getNumberFromString(inputOperand);
        Double result = new Double(0);
        switch (currentOperation) {
            case addition:
                result = firstOperand + secondOperand;
                break;
            case subtraction:
                result = firstOperand - secondOperand;
                break;
            case multiplication:
                result = firstOperand * secondOperand;
                break;
            case division:
                if(secondOperand == 0) {
                    Toast toast = Toast.makeText(this, "Сan't divide by zero!",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                result = firstOperand / secondOperand;
                break;
        }
        сlear();
        setInputOperand(DouleToString(result));
    }

    private String DouleToString(Double d) {
        NumberFormat nf = new DecimalFormat("#.###########################");
        return nf.format(d);
    }
}