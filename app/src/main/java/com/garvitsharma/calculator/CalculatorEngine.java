package com.garvitsharma.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Pure Java calculator logic with no Android dependencies, so it can be unit tested.
 *
 * The calculator works like a basic pocket calculator: enter a number, choose an
 * operator, enter a second number and press equals. Pressing a second operator
 * straight after a result keeps chaining on that result.
 */
public class CalculatorEngine {

    public static final int MAX_DIGITS = 15;
    private static final MathContext MATH_CONTEXT = new MathContext(12);
    private static final BigDecimal LARGE_VALUE = new BigDecimal("1E12");

    /** Kinds of errors the engine can report. */
    public enum Error {
        NONE,
        DIVIDE_BY_ZERO,
        INVALID_INPUT
    }

    private String entry = "0";
    private boolean startNewEntry = false;
    private BigDecimal accumulator = null;
    private Operator pendingOperator = null;
    private String expression = "";
    private Error error = Error.NONE;

    public String getDisplay() {
        return entry;
    }

    public String getExpression() {
        return expression;
    }

    public Error getError() {
        return error;
    }

    public boolean hasError() {
        return error != Error.NONE;
    }

    public void inputDigit(int digit) {
        if (digit < 0 || digit > 9) {
            error = Error.INVALID_INPUT;
            return;
        }
        if (prepareForInput()) {
            entry = String.valueOf(digit);
            return;
        }
        if (digitCount(entry) >= MAX_DIGITS) {
            return; // ignore extra digits instead of overflowing the screen
        }
        entry = entry.equals("0") ? String.valueOf(digit)
                : entry.equals("-0") ? "-" + digit
                : entry + digit;
    }

    public void inputDecimal() {
        if (prepareForInput()) {
            entry = "0.";
            return;
        }
        if (!entry.contains(".")) {
            entry = entry + ".";
        }
    }

    public void negate() {
        if (hasError()) {
            return;
        }
        if (startNewEntry && pendingOperator != null) {
            entry = "-0";
            startNewEntry = false;
            return;
        }
        if (entry.startsWith("-")) {
            entry = entry.substring(1);
        } else if (!isZero(entry) || entry.equals("0.")) {
            entry = "-" + entry;
        } else {
            entry = "-0";
        }
    }

    public void backspace() {
        if (hasError()) {
            clear();
            return;
        }
        if (startNewEntry) {
            return;
        }
        if (entry.length() <= 1 || (entry.length() == 2 && entry.startsWith("-"))) {
            entry = "0";
        } else {
            entry = entry.substring(0, entry.length() - 1);
        }
    }

    public void clear() {
        entry = "0";
        startNewEntry = false;
        accumulator = null;
        pendingOperator = null;
        expression = "";
        error = Error.NONE;
    }

    public void setOperator(Operator operator) {
        if (hasError()) {
            return;
        }
        if (pendingOperator != null && !startNewEntry) {
            // chain: 2 + 3 + ... computes 2 + 3 first
            if (!applyPending()) {
                return;
            }
        } else if (pendingOperator == null) {
            accumulator = parse(entry);
        }
        pendingOperator = operator;
        startNewEntry = true;
        expression = format(accumulator) + " " + operator.getSymbol();
        entry = format(accumulator);
    }

    public void equals() {
        if (hasError() || pendingOperator == null) {
            return;
        }
        String left = format(accumulator);
        Operator op = pendingOperator;
        String right = entry;
        if (applyPending()) {
            expression = left + " " + op.getSymbol() + " " + right + " =";
            pendingOperator = null;
            accumulator = null;
            startNewEntry = true;
        }
    }

    // ---- helpers -------------------------------------------------------------

    /** Clears an error or a finished entry. Returns true if the entry was reset. */
    private boolean prepareForInput() {
        if (hasError()) {
            clear();
        }
        if (startNewEntry) {
            if (pendingOperator == null) {
                // starting over after "=": drop the old expression
                expression = "";
            }
            startNewEntry = false;
            return true;
        }
        return false;
    }

    private boolean applyPending() {
        BigDecimal right = parse(entry);
        if (right == null || accumulator == null) {
            fail(Error.INVALID_INPUT);
            return false;
        }
        BigDecimal result;
        switch (pendingOperator) {
            case ADD:
                result = accumulator.add(right);
                break;
            case SUBTRACT:
                result = accumulator.subtract(right);
                break;
            case MULTIPLY:
                result = accumulator.multiply(right);
                break;
            case DIVIDE:
                if (right.signum() == 0) {
                    fail(Error.DIVIDE_BY_ZERO);
                    return false;
                }
                result = accumulator.divide(right, MATH_CONTEXT);
                break;
            default:
                fail(Error.INVALID_INPUT);
                return false;
        }
        accumulator = result.round(MATH_CONTEXT);
        entry = format(accumulator);
        startNewEntry = true;
        return true;
    }

    private void fail(Error e) {
        error = e;
        entry = "0";
        accumulator = null;
        pendingOperator = null;
        startNewEntry = true;
        expression = "";
    }

    private static BigDecimal parse(String text) {
        try {
            String t = text.endsWith(".") ? text + "0" : text;
            return new BigDecimal(t);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isZero(String text) {
        BigDecimal v = parse(text);
        return v != null && v.signum() == 0;
    }

    private static int digitCount(String text) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }

    /** Formats a number without trailing zeros, switching to scientific notation if huge. */
    static String format(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        if (value.signum() == 0) {
            return "0";
        }
        BigDecimal stripped = value.stripTrailingZeros();
        if (stripped.abs().compareTo(LARGE_VALUE) >= 0
                || stripped.abs().compareTo(new BigDecimal("1E-9")) < 0) {
            return stripped.round(new MathContext(8)).toString();
        }
        return stripped.toPlainString();
    }
}
