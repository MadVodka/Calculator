package ivan.vatlin.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalculatorImpl implements Calculator {
    @Override
    public String calculate(String inputData) throws Exception {
        String rpn = reversePolishNotation(inputData);

        Deque<Character> numbersStack = new ArrayDeque<>();
        for (int i = 0; i < rpn.length(); i++) {
            char currentChar = inputData.charAt(i);
            if (Character.isDigit(currentChar)) {
                numbersStack.push(currentChar);
                continue;
            }

            double b = (double) numbersStack.pop();
            double a = (double) numbersStack.pop();

            char result;
            switch (currentChar) {
                case '^':
                    result = (char)Math.pow(a,b);
                    break;
                case '*':
                    result = (char)(a*b);
                    break;
                case ':':
                    result = (char)(a/b);
                    break;
                case '+':
                    result = (char)(a+b);
                    break;
                case '-':
                    result = (char)(a-b);
                    break;
                default:
                    throw new Exception("Используются недопустимые операторы");
            }
            numbersStack.push(result);
        }
        return null;
    }

    private String reversePolishNotation(String inputData) {
        StringBuilder output = new StringBuilder();
        Deque<Character> operatorsStack = new ArrayDeque<>();

        for (int i = 0; i < inputData.length(); i++) {
            char currentChar = inputData.charAt(i);

            if (isOperator(currentChar)) {
                Character lastOperator = operatorsStack.peek();

                if (lastOperator != null && lastOperator == '(') {
                    operatorsStack.push(currentChar);
                    continue;
                }

                while (lastOperator != null && priority(currentChar) <= priority(lastOperator)) {
                    if (currentChar != lastOperator) {
                        output.append(operatorsStack.pop());
                        lastOperator = operatorsStack.peek();
                    } else {
                        lastOperator = null;
                    }
                }
                operatorsStack.push(currentChar);
            } else if (currentChar == '(') {
                operatorsStack.push(currentChar);
            } else if (currentChar == ')') {
                Character lastOperator = operatorsStack.poll();
                while (lastOperator != null && lastOperator != '(') {
                    output.append(lastOperator);
                    lastOperator = operatorsStack.poll();
                }
            } else {
                output.append(currentChar).append(" ");
            }
        }

        Character leftOperators = operatorsStack.poll();
        while (leftOperators != null) {
            output.append(leftOperators);
            leftOperators = operatorsStack.poll();
        }

        return output.toString();
    }

    private boolean isOperator(char symbol) {
        switch (symbol) {
            case '*':
            case ':':
            case '+':
            case '-':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private int priority(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case ':':
                return 2;
            default:
                return 1;
        }
    }
}
