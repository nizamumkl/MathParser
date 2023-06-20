import co.etomo.MathParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathParserTest {

    @Test
    public void whenAddition_thenEqual() {
        String expression = "1 + 2";
        assertEquals(MathParser.evaluate(expression), 3.0, 0.0);
    }

    @Test
    public void whenSubtraction_thenEqual() {
        String expression = "1 - 2";
        assertEquals(MathParser.evaluate(expression), -1.0, 0.0);
    }

    @Test
    public void whenMultiplication_thenEqual() {
        String expression = "1 * 2";
        assertEquals(MathParser.evaluate(expression), 2.0, 0.0);
    }

    @Test
    public void whenDivision_thenEqual() {
        String expression = "1 / 2";
        assertEquals(MathParser.evaluate(expression), 0.5, 0.0);
    }

    @Test
    public void whenHasBrackets_thenEqual() {
        String expression = "-1 + 20 - (2 * 3 + (6 / 3))";
        assertEquals(MathParser.evaluate(expression), 11.0, 0.0);
    }

    @Test
    public void whenMissingCloseBracketExceptionMessage_thenEqual() {
        try {
            String expression = "(1 + 2";
        } catch (RuntimeException e) {
            assertEquals("Missing ')'", e.getMessage());
        }
    }

    @Test
    public void whenUnexpectedCharExceptionMessage_thenEqual() {
        char unExpectedChar = '#';
        try {
            String expression = "1 " + unExpectedChar + " 2";
        } catch (RuntimeException e) {
            assertEquals("Unexpected: " + unExpectedChar, e.getMessage());
        }
    }

}
