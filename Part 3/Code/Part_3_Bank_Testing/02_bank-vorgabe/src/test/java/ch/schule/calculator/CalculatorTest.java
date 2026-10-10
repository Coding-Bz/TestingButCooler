package ch.schule.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calc;

    @BeforeEach
    void setUp() { calc = new Calculator(); }

    @Test void add() {
        assertEquals(13, calc.add(6, 7));
        assertEquals(27, calc.add(29, -2));
    }

    @Test void subtract() {
        assertEquals(-1, calc.subtract(2, 3));
        assertEquals(34, calc.subtract(35, 1));
    }

    @Test void multiply() {
        assertEquals(-6, calc.multiply(-2, 3));
        assertEquals(0, calc.multiply(0, 99));
    }

    @Test void divide() {
        assertEquals(2.5, calc.divide(5, 2));
        assertEquals(2, calc.divide(-6, -3));
    }

    @Test void divideByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
    }
}
