package ivan.vatlin.calculator;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalculatorImpl implements Calculator {
    @Override
    public String calculate(String inData) {
        return null;
    }

    private String reversePolishNotation(String rawInData) {
        StringBuilder output = new StringBuilder();
        Deque<Character> operatorsStack = new ArrayDeque<>();

        for (int i = 0; i < rawInData.length(); i++) {
            char currentChar = rawInData.charAt(i);

            if (isOperator(currentChar)) {
                char lastOperator = operatorsStack.getLast();

                while (priority(currentChar) <= priority(lastOperator)) {
                    if (currentChar != lastOperator) {
                        output.append(operatorsStack.pop());
                    }
                    operatorsStack.push(currentChar);
                }
                operatorsStack.push(currentChar); // ??????????


            } else if (currentChar == '(') {

            } else if (currentChar == ')') {

            } else {
                output.append(currentChar);
            }
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
