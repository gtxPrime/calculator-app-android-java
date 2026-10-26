package com.garvitsharma.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** Screen that shows the display and wires every key to the {@link CalculatorEngine}. */
public class MainActivity extends AppCompatActivity {

    private static final int[] DIGIT_IDS = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
    };

    private final CalculatorEngine engine = new CalculatorEngine();
    private TextView tvDisplay;
    private TextView tvExpression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvExpression = findViewById(R.id.tvExpression);

        setUpDigitButtons();
        setUpOperatorButtons();
        setUpFunctionButtons();
        refresh();
    }

    private void setUpDigitButtons() {
        for (int i = 0; i < DIGIT_IDS.length; i++) {
            final int digit = i;
            findViewById(DIGIT_IDS[i]).setOnClickListener(v -> {
                engine.inputDigit(digit);
                refresh();
            });
        }
    }

    private void setUpOperatorButtons() {
        bindOperator(R.id.btnAdd, Operator.ADD);
        bindOperator(R.id.btnSubtract, Operator.SUBTRACT);
        bindOperator(R.id.btnMultiply, Operator.MULTIPLY);
        bindOperator(R.id.btnDivide, Operator.DIVIDE);
    }

    private void bindOperator(int viewId, final Operator operator) {
        findViewById(viewId).setOnClickListener(v -> {
            engine.setOperator(operator);
            refresh();
        });
    }

    private void setUpFunctionButtons() {
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            engine.clear();
            refresh();
        });
        findViewById(R.id.btnBackspace).setOnClickListener(v -> {
            engine.backspace();
            refresh();
        });
        findViewById(R.id.btnNegate).setOnClickListener(v -> {
            engine.negate();
            refresh();
        });
        findViewById(R.id.btnDecimal).setOnClickListener(v -> {
            engine.inputDecimal();
            refresh();
        });
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            engine.equals();
            refresh();
        });
    }

    private void refresh() {
        tvExpression.setText(engine.getExpression());
        if (engine.hasError()) {
            int message = engine.getError() == CalculatorEngine.Error.DIVIDE_BY_ZERO
                    ? R.string.error_divide_by_zero
                    : R.string.error_invalid_input;
            tvDisplay.setText(message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            tvDisplay.setText(engine.getDisplay());
        }
    }
}
