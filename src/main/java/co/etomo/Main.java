package co.etomo;

public class Main {

    public static void main(String[] args) {

        String expression = "-1 + 20 - (2 * 3 + (6 / 3))";
        double output = MathParser.evaluate(expression);
        System.out.println(expression + " = " + output);

    }

}