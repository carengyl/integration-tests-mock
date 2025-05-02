package functions.stubs.trigonometric;

import static functions.stubs.trigonometric.TanStub.tan;

public class CotStub {
    public static double cot(double x) {
        return 1/tan(x);
    }
}
