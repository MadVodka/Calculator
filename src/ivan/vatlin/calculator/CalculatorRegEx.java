package ivan.vatlin.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorRegEx {
    String expression = "3.412+0.31*2:(1-5)^2^3";
    String elementRegEx = "((\\d+([\\.]\\d+)?)|[\\+\\-\\*:^()])";

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}

    // [\+\-\*:^\.]{2,}|\)\(|\([\.\+\*:^]|([\.\+\-\*:^]\))|(\d+[.]){2,}|0{2,}[.]|[^.]\b0+\d

    public static void main(String[] args) {
        CalculatorRegEx calculatorRegEx = new CalculatorRegEx();
        calculatorRegEx.test();
    }

    private void test() {
        Pattern pattern = Pattern.compile(elementRegEx);
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
