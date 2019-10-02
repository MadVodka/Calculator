package ivan.vatlin.calculator;

public class Application {
    public static void main(String[] args) {
        String mathExpression = "7+(8*4):2+0.52";
//        String mathExpression = "3+4*2:(1-5)^2^3";

        Calculator calculator = new CalculatorImpl();
        String result = null;
        try {
            result = calculator.calculate(mathExpression);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println(result);
    }
}
