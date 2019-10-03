package ivan.vatlin.calculator.exceptions;

public class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException(String s) {
        super(s);
    }
}
