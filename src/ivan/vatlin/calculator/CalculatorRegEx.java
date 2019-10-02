package ivan.vatlin.calculator;

public class CalculatorRegEx {
    String expression = "3.412+0.31*2:(1-5)^2^3";
    public static final String OPERANDS_REG_EX = "((\\d+([\\.]\\d+)?)|[\\+\\-\\*:^()])";
    public static final String NUMBERS_REG_EX = "(\\d+([\\.]\\d+)?)";
    public static final String OPERATORS_REG_EX = "[\\+\\-\\*:^]";
    public static final String BRACES_REG_EX = "[()]";
    public static final String RESTRICTED_CASES_REG_EX = "^[\\+\\*:^\\.)]|[^\\+\\-\\*:^\\.\\d()]|" +
            "[\\+\\-\\*:^\\.]{2,}|\\)\\(|\\(\\)|\\([\\.\\+\\*:^]|([\\.\\+\\-\\*:^]\\))|" +
            "(\\d+[.]){2,}|\\b0\\d+[.]|[\\+\\-\\*:^\\.(]$";

    // https://regex101.com/r/h1t2aO/2
}
