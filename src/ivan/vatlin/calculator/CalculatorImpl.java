package ivan.vatlin.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorImpl implements Calculator {
    @Override
    public String calculate(String inputData) throws Exception {
        List<String> rpn = reversePolishNotation(inputData);
        Deque<String> numbersStack = new ArrayDeque<>();

        for (String operand : rpn) {

            if (Pattern.matches(CalculatorRegEx.CALCULATOR_NUMBERS, operand)) {
                numbersStack.push(operand);
            } else if (Pattern.matches(CalculatorRegEx.CALCULATOR_OPERATORS, operand)) {
                Double secondDouble = Double.valueOf(numbersStack.pop());
                Double firstDouble = Double.valueOf(numbersStack.pop());

                String result = calculateExpression(firstDouble, secondDouble, operand);
                numbersStack.push(result);
            } else if (Pattern.matches(CalculatorRegEx.CALCULATOR_BRACES, operand)) {
                throw new Exception("Используются лишние скобки");
            } else {
                throw new Exception("Использование других символов запрещено");
            }
        }
        return numbersStack.pop();
    }

    private List<String> reversePolishNotation(String inputData) {
        List<String> output = new ArrayList<>();
        Deque<String> operatorsStack = new ArrayDeque<>();

        Pattern pattern = Pattern.compile(CalculatorRegEx.CALCULATOR_OPERANDS_REG_EX);
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

        System.out.println(output);
        return output;
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
