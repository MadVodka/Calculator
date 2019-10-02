package ivan.vatlin.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorRegEx {
    String expression = "3.412+0.31*2:(1-5)^2^3";
    public static final String CALCULATOR_OPERANDS_REG_EX = "((\\d+([\\.]\\d+)?)|[\\+\\-\\*:^()])";
    public static final String CALCULATOR_NUMBERS = "(\\d+([\\.]\\d+)?)";
    public static final String CALCULATOR_OPERATORS = "[\\+\\-\\*:^]";
    public static final String CALCULATOR_BRACES = "[()]";

    // https://regex101.com/r/h1t2aO/2

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}|0{2,}[.]|[^.]\b0+\d

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}|0{2,}[.]|[^.]\b0+\d\b\

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}|\b0\d+[.]

    // [^\+\-\*:^\.\d)(]|[\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}|\b0\d+[.]|\b[^\.1-9]0+[1-9]

    public static void main(String[] args) {
        CalculatorRegEx calculatorRegEx = new CalculatorRegEx();
        calculatorRegEx.test();
    }

    private void test() {
        Pattern pattern = Pattern.compile(CALCULATOR_OPERANDS_REG_EX);
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
