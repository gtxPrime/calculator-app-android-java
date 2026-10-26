package com.garvitsharma.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CalculatorEngineTest {

    private CalculatorEngine engine;

    @Before
    public void setUp() {
        engine = new CalculatorEngine();
    }

    private void enter(String number) {
        for (char c : number.toCharArray()) {
            if (c == '.') {
                engine.inputDecimal();
            } else {
                engine.inputDigit(c - '0');
            }
        }
    }

    @Test
    public void addition() {
        enter("12");
        engine.setOperator(Operator.ADD);
        enter("30");
        engine.equals();
        assertEquals("42", engine.getDisplay());
    }

    @Test
    public void subtractionCanGoNegative() {
        enter("5");
        engine.setOperator(Operator.SUBTRACT);
        enter("8");
        engine.equals();
        assertEquals("-3", engine.getDisplay());
    }

    @Test
    public void multiplicationWithDecimals() {
        enter("1.5");
        engine.setOperator(Operator.MULTIPLY);
        enter("4");
        engine.equals();
        assertEquals("6", engine.getDisplay());
    }

    @Test
    public void divisionGivesDecimal() {
        enter("1");
        engine.setOperator(Operator.DIVIDE);
        enter("4");
        engine.equals();
        assertEquals("0.25", engine.getDisplay());
    }

    @Test
    public void divisionRepeatingIsRounded() {
        enter("1");
        engine.setOperator(Operator.DIVIDE);
        enter("3");
        engine.equals();
        assertEquals("0.333333333333", engine.getDisplay());
    }

    @Test
    public void divideByZeroReportsError() {
        enter("9");
        engine.setOperator(Operator.DIVIDE);
        enter("0");
        engine.equals();
        assertTrue(engine.hasError());
        assertEquals(CalculatorEngine.Error.DIVIDE_BY_ZERO, engine.getError());
    }

    @Test
    public void errorClearsOnNextDigit() {
        enter("9");
        engine.setOperator(Operator.DIVIDE);
        enter("0");
        engine.equals();
        enter("7");
        assertFalse(engine.hasError());
        assertEquals("7", engine.getDisplay());
    }

    @Test
    public void clearResetsEverything() {
        enter("9");
        engine.setOperator(Operator.ADD);
        enter("1");
        engine.clear();
        assertEquals("0", engine.getDisplay());
        assertEquals("", engine.getExpression());
    }

    @Test
    public void chainingOperatorsComputesInOrder() {
        enter("2");
        engine.setOperator(Operator.ADD);
        enter("3");
        engine.setOperator(Operator.MULTIPLY);
        assertEquals("5", engine.getDisplay());
        enter("4");
        engine.equals();
        assertEquals("20", engine.getDisplay());
    }

    @Test
    public void operatorAfterResultContinuesFromResult() {
        enter("6");
        engine.setOperator(Operator.ADD);
        enter("4");
        engine.equals();
        engine.setOperator(Operator.SUBTRACT);
        enter("3");
        engine.equals();
        assertEquals("7", engine.getDisplay());
    }

    @Test
    public void onlyOneDecimalPointAllowed() {
        enter("1.2.3");
        assertEquals("1.23", engine.getDisplay());
    }

    @Test
    public void leadingZerosAreCollapsed() {
        enter("007");
        assertEquals("7", engine.getDisplay());
    }

    @Test
    public void digitLimitIsEnforced() {
        enter("12345678901234567890");
        assertEquals(CalculatorEngine.MAX_DIGITS, engine.getDisplay().length());
    }

    @Test
    public void backspaceRemovesLastCharacter() {
        enter("123");
        engine.backspace();
        assertEquals("12", engine.getDisplay());
        engine.backspace();
        engine.backspace();
        assertEquals("0", engine.getDisplay());
    }

    @Test
    public void negateToggleSign() {
        enter("5");
        engine.negate();
        assertEquals("-5", engine.getDisplay());
        engine.negate();
        assertEquals("5", engine.getDisplay());
    }

    @Test
    public void equalsWithoutOperatorDoesNothing() {
        enter("8");
        engine.equals();
        assertEquals("8", engine.getDisplay());
    }

    @Test
    public void decimalAdditionIsExact() {
        enter("0.1");
        engine.setOperator(Operator.ADD);
        enter("0.2");
        engine.equals();
        assertEquals("0.3", engine.getDisplay());
    }
}
