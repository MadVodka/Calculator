package ivan.vatlin.calculator.calculator;

import ivan.vatlin.calculator.exceptions.CalculatorValidationException;
import ivan.vatlin.calculator.exceptions.DivideByZeroException;
import ivan.vatlin.calculator.exceptions.WrongOperandsException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ivan.vatlin.calculator.calculator.CalculationOperators.*;

public class Calculator {

//    public static void main(String[] args) throws CalculatorValidationException, WrongOperandsException {
//        Calculator calculator = new Calculator();
//        System.out.println(calculator.calculate("vasya"));
//    }
    public String calculate(String inputExpression) throws WrongOperandsException, CalculatorValidationException {
        String preparedInputExpression = prepareInputExpression(inputExpression);
        String validatedInputExpression = validateInputExpression(preparedInputExpression);

        PolishNotation polishNotation = new PolishNotation(validatedInputExpression, CalculatorRegEx.OPERANDS_REG_EX);
        List<String> reversePolishNotation = polishNotation.getReversePolishNotation();

        Deque<String> numbersStack = new ArrayDeque<>();
        for (String operand : reversePolishNotation) {
            if (Pattern.matches(CalculatorRegEx.NUMBERS_REG_EX, operand)) {
                numbersStack.push(operand);
            } else if (Pattern.matches(CalculatorRegEx.OPERATORS_REG_EX, operand)) {
                Double secondDouble = convertStringToDouble(numbersStack.poll());
                Double firstDouble = convertStringToDouble(numbersStack.poll());

                String result = doOperationOnDoubles(firstDouble, secondDouble, operand);
                numbersStack.push(result);
            } else if (Pattern.matches(CalculatorRegEx.BRACES_REG_EX, operand)) {
                throw new WrongOperandsException("Используются лишние скобки");
            } else {
                throw new WrongOperandsException("Использование других символов запрещено: " + operand);
            }
        }
        return numbersStack.pop();
    }

    private String prepareInputExpression(String inputExpression) {
        return inputExpression.replaceAll("\\s", "");
    }

    private String validateInputExpression(String inputExpression) throws CalculatorValidationException {
        if (inputExpression.equals("")) {
            throw new CalculatorValidationException("Пустое выражение");
        }

        Pattern pattern = Pattern.compile(CalculatorRegEx.RESTRICTED_CASES_REG_EX);
        Matcher matcher = pattern.matcher(inputExpression);

        if (matcher.find()) {
            StringBuilder stringBuilder = new StringBuilder(inputExpression);
            stringBuilder.insert(matcher.start(), " --> ");

            throw new CalculatorValidationException("Ошибка: " + stringBuilder.toString());
        }
        return inputExpression;
    }

    private Double convertStringToDouble(String doubleAsString) throws NumberFormatException {
        return Stream.of(doubleAsString)
//                .filter(Objects::nonNull)
                .mapToDouble(Double::valueOf)
                .findFirst()
                .orElse(0);
    }

    private String doOperationOnDoubles(Double firstDouble, Double secondDouble, String operator)
            throws DivideByZeroException, WrongOperandsException {
        if (firstDouble == 0) {
            if (operator.equals(MINUS)) {
                return operator + secondDouble;
            }
            return Double.toString(secondDouble);
        }

        double result;

        switch (operator) {
            case POWER:
                result = Math.pow(firstDouble, secondDouble);
                break;
            case MULTIPLY:
                result = firstDouble * secondDouble;
                break;
            case DIVIDE:
                if (secondDouble == 0) {
                    throw new DivideByZeroException("Деление на ноль невозможно");
                }
                result = firstDouble / secondDouble;
                break;
            case PLUS:
                result = firstDouble + secondDouble;
                break;
            case MINUS:
                result = firstDouble - secondDouble;
                break;
            default:
                throw new WrongOperandsException("Используются недопустимые операторы");
        }

        return Double.toString(result);
    }
}
