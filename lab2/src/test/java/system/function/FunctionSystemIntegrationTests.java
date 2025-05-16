package system.function;

import functions.FunctionSystem;
import functions.LogarithmFunction;
import functions.TrigonometricFunctions;
import functions.taylor.LnTaylor;
import functions.taylor.SinTaylor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FunctionSystemIntegrationTests {
    private FunctionSystem functionSystemWithStubs;
    private FunctionSystem functionSystemWithRealImpl;

    @BeforeEach
    void setUp() {
        functionSystemWithStubs = new FunctionSystem();

        TrigonometricFunctions realTrigs = new TrigonometricFunctions(new SinTaylor()::sin);
        LogarithmFunction realLogs = new LogarithmFunction(new LnTaylor()::ln);
        functionSystemWithRealImpl = new FunctionSystem(realTrigs, realLogs);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI, 0.0})
    void calculate_ShouldReturnCorrectResult_ForNonPositiveValues(double x) {
        double stubResult = functionSystemWithStubs.calculate(x);

        assertTrue(Double.isNaN(stubResult));
        assertFalse(Double.isInfinite(stubResult));

        if (x != 0) {
            double realResult = functionSystemWithRealImpl.calculate(x);
            assertTrue(Double.isNaN(realResult));
        }
    }

    @Test
    void calculate_ShouldHandleSpecialTrigCases() {
        assertDoesNotThrow(() -> functionSystemWithStubs.calculate(-Math.PI/2));
        assertDoesNotThrow(() -> functionSystemWithRealImpl.calculate(-Math.PI/4));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, 2.0, 5.0, 10.0})
    void calculate_ShouldReturnCorrectResult_ForPositiveValues(double x) {
        double stubResult = functionSystemWithStubs.calculate(x);

        assertAll(
                () -> assertFalse(Double.isNaN(stubResult)),
                () -> assertFalse(Double.isInfinite(stubResult))
        );

        if (x > 0) {
            double realResult = functionSystemWithRealImpl.calculate(x);
            assertFalse(Double.isNaN(realResult));
            assertTrue(realResult >= 0);
        }
    }

    @Test
    void calculate_ShouldHandleLogEdgeCases() {
        assertDoesNotThrow(() -> functionSystemWithStubs.calculate(Double.MIN_VALUE));
        assertDoesNotThrow(() -> functionSystemWithRealImpl.calculate(1.0));
    }

    @Test
    void shouldCorrectlyIntegrateTrigonometricAndLogarithmicParts() {
        double negativeResult = functionSystemWithRealImpl.calculate(-0.01);
        double positiveResult = functionSystemWithRealImpl.calculate(0.01);

        assertNotEquals(negativeResult, positiveResult);

        assertAll(
                () -> assertDoesNotThrow(() -> functionSystemWithRealImpl.calculate(0)),
                () -> assertNotEquals(
                        functionSystemWithRealImpl.calculate(-1e-10),
                        functionSystemWithRealImpl.calculate(1e-10))
        );
    }
}