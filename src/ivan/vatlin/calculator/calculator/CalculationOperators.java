package ivan.vatlin.calculator.calculator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculationOperators {
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = ":";
    public static final String POWER = "^";
    public static final String OPEN_BRACE = "(";
    public static final String CLOSE_BRACE = ")";

    public static final Set<String> BRACES = Stream.of(OPEN_BRACE, CLOSE_BRACE).collect(Collectors.toSet());
    public static final Set<String> OPERATORS = Stream.of(PLUS, MINUS, MULTIPLY, DIVIDE, POWER)
            .collect(Collectors.toSet());
}
