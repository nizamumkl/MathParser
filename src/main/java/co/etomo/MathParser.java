package co.etomo;

public class MathParser {
    private static final char EMPTY_SPACE = ' ';
    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char STAR = '*';
    private static final char SLASH = '/';
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';

    private static int currPosition = -1, currChar;
    private static String strInput;
    
    public static double evaluate(final String str) {
        strInput = str;
        return parse();
    }

    private static void goNextChar() {
        currChar = (++currPosition < strInput.length()) ? strInput.charAt(currPosition) : -1;
    }

    private static boolean isProcessing(int charToProcess){
        // refer char to int mapping here: https://www.rapidtables.com/code/text/ascii-table.html

        while (currChar == EMPTY_SPACE)
            goNextChar();

        if (currChar == charToProcess) {
            goNextChar();
            return true;
        }

        return false;
    }

    private static double parse() {
        goNextChar();
        double x = parseExpression();

        if (currPosition < strInput.length())
            throw new RuntimeException("Unexpected: " + (char) currChar);

        return x;
    }

    private static double parseExpression() {
        // expression ==> term | expression `+` term | expression `-` term

        double x = parseTerm();
        for (;;) {
            if (isProcessing(PLUS)) {
                x += parseTerm(); // addition
            } else if (isProcessing(MINUS)) {
                x -= parseTerm(); // subtraction
            } else return x;
        }
    }

    private static double parseTerm() {
        // term ==> factor | term `*` factor | term `/` factor

        double x = parseFactor();
        for (;;) { // while true
            if (isProcessing(STAR)) {
                x *= parseFactor(); // multiplication
            } else if (isProcessing(SLASH)) {
                x /= parseFactor(); // division
            } else return x;
        }
    }

    private static double parseFactor() {
        // factor ==> `+` factor | `-` factor | `(` expression `)` | number

        if (isProcessing(PLUS)) return +parseFactor(); // unary plus
        if (isProcessing(MINUS)) return -parseFactor(); // unary minus

        double x;

        if (isProcessing(OPEN_BRACKET)) { // parentheses @ bracket
            x = parseExpression();
            if (!isProcessing(CLOSE_BRACKET)) throw new RuntimeException("Missing ')'");
        } else if (isNumber(currChar)) { // numbers
            int startPos = currPosition;
            x = getNumber(startPos);
        } else {
            throw new RuntimeException("Unexpected: " + (char) currChar);
        }

        return x;
    }

    private static boolean isNumber(int inChar) {
        return ((inChar >= '0' && inChar <= '9') || inChar == '.') ? true : false;
    }

    private static double getNumber(int startPos) {
        while (isNumber(currChar)) goNextChar();
        return Double.parseDouble(strInput.substring(startPos, currPosition));
    }

}
