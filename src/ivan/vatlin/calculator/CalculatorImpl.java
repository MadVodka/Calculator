package ivan.vatlin.calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CalculatorImpl implements Calculator {
    @Override
    public String calculate(String inputExpression) throws Exception {
        String preparedInputExpression = prepareInputExpression(inputExpression);
        String validatedInputExpression = validateInputExpression(preparedInputExpression);

        List<String> reversePolishNotation = reversePolishNotation(validatedInputExpression);
        Deque<String> numbersStack = new ArrayDeque<>();

        for (String operand : reversePolishNotation) {
            if (Pattern.matches(CalculatorRegEx.NUMBERS_REG_EX, operand)) {
                numbersStack.push(operand);
            } else if (Pattern.matches(CalculatorRegEx.OPERATORS_REG_EX, operand)) {
                Double secondDouble = convertStringToDouble(numbersStack.poll());
                Double firstDouble = convertStringToDouble(numbersStack.poll());

                String result = calculateExpression(firstDouble, secondDouble, operand);
                numbersStack.push(result);
            } else if (Pattern.matches(CalculatorRegEx.BRACES_REG_EX, operand)) {
                throw new Exception("Используются лишние скобки");
            } else {
                throw new Exception("Использование других символов запрещено");
            }
        }
        return numbersStack.pop();
    }

    private String prepareInputExpression(String inputExpression) {
        return inputExpression.replaceAll("\\s", "");
    }

    private String validateInputExpression(String inputExpression) throws Exception {
        Pattern pattern = Pattern.compile(CalculatorRegEx.RESTRICTED_CASES_REG_EX);
        Matcher matcher = pattern.matcher(inputExpression);

        if (matcher.find()) {
            throw new Exception("Проверьте правильность написания выражения: " + matcher.group());
        }
        return inputExpression;
    }

    private List<String> reversePolishNotation(String inputData) {
        List<String> output = new ArrayList<>();
        Deque<String> operatorsStack = new ArrayDeque<>();

        Pattern pattern = Pattern.compile(CalculatorRegEx.OPERANDS_REG_EX);
        Matcher matcher = pattern.matcher(inputData);

        while (matcher.find()) {
            String currentOperand = matcher.group();

            if (isOperator(currentOperand)) {
                String lastOperator = operatorsStack.peek();

                if (lastOperator != null && lastOperator.equals("(")) {
                    operatorsStack.push(currentOperand);
                    continue;
                }

                while (lastOperator != null && priority(currentOperand) <= priority(lastOperator)) {
                    if (!currentOperand.equals(lastOperator)) {
                        output.add(operatorsStack.pop());
                        lastOperator = operatorsStack.peek();
                    } else {
                        lastOperator = null;
                    }
                }
                operatorsStack.push(currentOperand);
            } else if (currentOperand.equals("(")) {
                operatorsStack.push(currentOperand);
            } else if (currentOperand.equals(")")) {
                String lastOperator = operatorsStack.poll();
                while (lastOperator != null && !lastOperator.equals("(")) {
                    output.add(lastOperator);
                    lastOperator = operatorsStack.poll();
                }
            } else {
                output.add(currentOperand);
            }
        }

        String leftOperators = operatorsStack.poll();
        while (leftOperators != null) {
            output.add(leftOperators);
            leftOperators = operatorsStack.poll();
        }

        return output;
    }

    private Double convertStringToDouble(String doubleAsString) {
        return Stream.of(doubleAsString)
                .filter(Objects::nonNull)
                .mapToDouble(Double::valueOf)
                .findFirst()
                .orElse(0);
    }

    private boolean isOperator(String operand) {
        switch (operand) {
            case "*":
            case ":":
            case "+":
            case "-":
            case "^":
                return true;
            default:
                return false;
        }
    }

    private int priority(String operator) {
        switch (operator) {
            case "^":
                return 3;
            case "*":
            case ":":
                return 2;
            default:
                return 1;
        }
    }

    private String calculateExpression(Double firstDouble, Double secondDouble, String operator) throws Exception {
        if (firstDouble == 0) {
            if (operator.equals("-")) {
                return operator + secondDouble;
            }
            return Double.toString(secondDouble);
        }

        double result;

        switch (operator) {
            case "^":
                result = Math.pow(firstDouble, secondDouble);
                break;
            case "*":
                result = firstDouble * secondDouble;
                break;
            case ":":
                result = firstDouble / secondDouble;
                break;
            case "+":
                result = firstDouble + secondDouble;
                break;
            case "-":
                result = firstDouble - secondDouble;
                break;
            default:
                throw new Exception("Используются недопустимые операторы");
        }

        return Double.toString(result);
    }


}
