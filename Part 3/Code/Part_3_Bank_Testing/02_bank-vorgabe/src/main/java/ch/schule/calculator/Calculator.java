package ch.schule.calculator;

public class Calculator {
    // Addition
    public double add(double a, double b) { return a + b; }

    //Substraction
    public double subtract(double a, double b) { return a - b; }

    //Multiplication
    public double multiply(double a, double b) { return a * b; }

    //Division
    public double divide(double a, double b) {
        if (b == 0) throw new ArithmeticException("Invalid");
        return a / b;
    }
}
