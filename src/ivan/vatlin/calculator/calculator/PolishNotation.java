package ivan.vatlin.calculator.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolishNotation {
    private static final String OPEN_BRACE = "(";
    private static final String CLOSE_BRACE = ")";
    private String inputExpression;
    private String operandsRegEx;
    private List<String> outputOperands;
    private Deque<String> operatorsStack;

    public PolishNotation(String inputExpression, String operandsRegEx) {
        this.inputExpression = inputExpression;
        this.operandsRegEx = operandsRegEx;
        outputOperands = new ArrayList<>();
        operatorsStack = new ArrayDeque<>();
    }

    public List<String> getReversePolishNotation() {
        Pattern pattern = Pattern.compile(operandsRegEx);
        Matcher matcher = pattern.matcher(inputExpression);

        while (matcher.find()) {
            String currentOperand = matcher.group();

            if (isOperator(currentOperand)) {
                doOperationOnOperator(currentOperand);
            } else if (isBrace(currentOperand)) {
                doOperationOnBrace(currentOperand);
            } else {
                outputOperands.add(currentOperand);
            }
        }

        String leftOperators = operatorsStack.poll();
        while (leftOperators != null) {
            outputOperands.add(leftOperators);
            leftOperators = operatorsStack.poll();
        }

        return outputOperands;
    }

    private void doOperationOnOperator(String operator) {
        String lastOperator = operatorsStack.peek();

        if (lastOperator != null && lastOperator.equals("(")) {
            operatorsStack.push(operator);
            return;
        }

        while (lastOperator != null && priority(operator) <= priority(lastOperator)) {
            if (!operator.equals(lastOperator)) {
                outputOperands.add(operatorsStack.pop());
                lastOperator = operatorsStack.peek();
            } else {
                lastOperator = null;
            }
        }
        operatorsStack.push(operator);
    }

    private void doOperationOnBrace(String brace) {
        if (brace.equals("(")) {
            operatorsStack.push(brace);
        } else if (brace.equals(")")) {
            String lastOperator = operatorsStack.poll();
            while (lastOperator != null && !lastOperator.equals("(")) {
                outputOperands.add(lastOperator);
                lastOperator = operatorsStack.poll();
            }
        }
    }

    private boolean isBrace(String operand) {
        return operand.equals(OPEN_BRACE) || operand.equals(CLOSE_BRACE);
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
}
