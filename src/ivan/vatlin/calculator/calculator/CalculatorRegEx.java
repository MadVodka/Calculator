package ivan.vatlin.calculator.calculator;

public class CalculatorRegEx {
    public static final String OPERANDS_REG_EX = "((\\d+([\\.]\\d+)?)|[\\+\\-\\*:^()])";
    public static final String NUMBERS_REG_EX = "(\\d+([\\.]\\d+)?)";
    public static final String OPERATORS_REG_EX = "[\\+\\-\\*:^]";
    public static final String BRACES_REG_EX = "[()]";
    public static final String RESTRICTED_CASES_REG_EX = "^[\\+\\*:^\\.)]|[^\\+\\-\\*:^\\.\\d()]|" +
            "[\\+\\-\\*:^\\.]{2,}|\\)\\(|\\(\\)|\\([\\.\\+\\*:^]|([\\.\\+\\-\\*:^]\\))|" +
            "(\\d+[.]){2,}|\\b0\\d+[.]|[\\+\\-\\*:^\\.(]$";

    private CalculatorRegEx() {
    }
}
