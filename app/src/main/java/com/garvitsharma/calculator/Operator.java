package com.garvitsharma.calculator;

/** The four arithmetic operations supported by the calculator. */
public enum Operator {
    ADD("+"),
    SUBTRACT("\u2212"),
    MULTIPLY("\u00D7"),
    DIVIDE("\u00F7");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
