package ivan.vatlin.calculator.calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ivan.vatlin.calculator.calculator.CalculationOperators.*;

public class PolishNotation {
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

        if (lastOperator != null && lastOperator.equals(OPEN_BRACE)) {
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
        if (OPEN_BRACE.equals(brace)) {
            operatorsStack.push(brace);
        } else if (CLOSE_BRACE.equals(brace)) {
            String lastOperator = operatorsStack.poll();
            while (lastOperator != null && !lastOperator.equals(OPEN_BRACE)) {
                outputOperands.add(lastOperator);
                lastOperator = operatorsStack.poll();
            }
        }
    }

    private boolean isBrace(String operand) {
        return BRACES.contains(operand);
    }

    private boolean isOperator(String operand) {
        return OPERATORS.contains(operand);
    }

    private int priority(String operator) {
        switch (operator) {
            case POWER:
                return 3;
            case MULTIPLY:
            case DIVIDE:
                return 2;
            case PLUS:
            case MINUS:
                return 1;
            default:
                return 0;
        }
    }
}
